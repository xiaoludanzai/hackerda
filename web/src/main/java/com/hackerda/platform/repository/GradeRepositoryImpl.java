package com.hackerda.platform.repository;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.dao.GradeDao;
import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.grade.GradeRepository;
import com.hackerda.platform.domain.grade.TermGradeBO;
import com.hackerda.platform.pojo.Grade;
import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.Term;
import com.hackerda.platform.pojo.constant.ErrorCode;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpEvaluationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class GradeRepositoryImpl implements GradeRepository {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private GradeSpiderFacade gradeSpiderFacade;
    @Autowired
    private GradeAdapter gradeAdapter;

    @Value("${spider.grade.timeout: 5000}")
    private int getGradeTimeout;

    private final ExecutorService gradeAutoUpdatePool = new MDCThreadPool(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "gradeSearch"));


    @Override
    public void save(List<GradeBO> gradeList) {
        if(CollectionUtils.isEmpty(gradeList)){
            return;
        }

        List<Grade> list = gradeList.stream().map(x -> gradeAdapter.toDO(x)).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(list)){
            return;
        }

        gradeDao.insertBatch(list);
    }

    @Override
    public void update(List<GradeBO> gradeList) {
        if(CollectionUtils.isEmpty(gradeList)){
            return;
        }

        List<Grade> list = gradeList.stream().map(x -> gradeAdapter.toDO(x)).collect(Collectors.toList());

        for (Grade grade : list) {
            gradeDao.updateByUniqueIndex(grade);
        }
    }

    @Override
    public void delete(GradeBO grade) {
        gradeDao.deleteByUniqueIndex(gradeAdapter.toDO(grade));
    }

    @Override
    public List<TermGradeBO> getAllByStudent(StudentUser student) {

        CompletableFuture<List<Grade>> currentFuture =
                CompletableFuture.supplyAsync(() -> gradeSpiderFacade.getCurrentTermGrade(student), gradeAutoUpdatePool);

        CompletableFuture<List<Grade>> schemeFuture =
                CompletableFuture.supplyAsync(() -> gradeSpiderFacade.getSchemeGrade(student), gradeAutoUpdatePool);

        CompletableFuture<List<Grade>> completableFuture = currentFuture.thenCombine(schemeFuture,
                (x, y) -> {
                    x.addAll(y);
                    return x;
                });

        List<GradeBO> gradeList;
        Exception exception = null;
        try {
            gradeList = completableFuture.get(getGradeTimeout, TimeUnit.MILLISECONDS)
                    .stream()
                    .map(x-> gradeAdapter.toBO(x))
                    .collect(Collectors.toList());

            gradeList = checkUpdate(student, gradeList);

        } catch (Exception e) {
            gradeList = gradeDao.getGradeByAccount(student.getAccount()).stream()
                            .map(x-> gradeAdapter.toBO(x))
                            .collect(Collectors.toList());
            exception = e;
        }

        List<TermGradeBO> termGradeList = gradeToTermGradeList(gradeList);

        if(exception != null){
            handleException(termGradeList, exception);
        }else {
            termGradeList.forEach(x-> x.setFetchSuccess(true));
        }

        return termGradeList;
    }


    private void handleException(List<TermGradeBO> termGradeList, Exception exception) {
        int errorCode;
        String msg;

        if(exception instanceof ExecutionException) {
            Throwable cause = exception.getCause();
            if (cause instanceof PasswordUnCorrectException) {
                errorCode = ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode();
                msg = "账号或密码错误";
            }
            else if (cause instanceof UrpEvaluationException) {
                errorCode = ErrorCode.Evaluation_ERROR.getErrorCode();
                msg = "评估未完成，无法查看成绩";
            } else {
                errorCode = ErrorCode.SYSTEM_ERROR.getErrorCode();
                msg = "评估未完成，无法查看成绩";
                log.error("get grade error", exception);
            }

        }else if(exception instanceof  TimeoutException) {
            errorCode = ErrorCode.SYSTEM_ERROR.getErrorCode();
            msg = "抓取超时";
        }else {
            errorCode = ErrorCode.SYSTEM_ERROR.getErrorCode();
            msg = exception.getMessage();
            log.error("get grade error", exception);
        }

        termGradeList.forEach(x-> {
            x.setErrorCode(errorCode);
            x.setFetchSuccess(false);
            x.setErrorMsg(msg);
        });

    }

    private List<TermGradeBO> gradeToTermGradeList(List<GradeBO> gradeList) {
        SchoolTime schoolTime = DateUtils.getCurrentSchoolTime();

        return gradeList.stream()
                .collect(Collectors.groupingBy(x -> new Term(x.getTermYear(), x.getTermOrder())))
                .entrySet().stream()
                .map(x -> new TermGradeBO()
                        .setTermYear(x.getKey().getTermYear())
                        .setTermOrder(x.getKey().getOrder())
                        .setGradeList((x.getValue()))
                        .setCurrentTerm(schoolTime.getTerm().equals(x.getKey()))
                )
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }


    /**
     * 返回新一个新成绩的list，旧的的成绩会被过滤
     */
    public List<GradeBO> checkUpdate(StudentUser student, List<GradeBO> gradeList) {
        List<GradeBO> gradeListFromDb = gradeDao.getGradeByAccount(student.getAccount())
                .stream().map(x-> gradeAdapter.toBO(x)).collect(Collectors.toList());

        // 如果数据库之前没有保存过该学号成绩，则直接返回抓取结果
        if (CollectionUtils.isEmpty(gradeListFromDb)) {
            gradeList.forEach(x-> x.setNewGrade(true));
            return gradeList;
        }

        // 课程得唯一id作为map的key
        Function<GradeBO, String> keyMapper = grade -> grade.getCourseNumber() + grade.getCourseOrder();

        // 这个逻辑是处理教务网同一个课程返回两个结果，那么选择有成绩的结果
        BinaryOperator<GradeBO> binaryOperator = (oldValue, newValue) -> {
            if (oldValue.getScore() == -1) {
                return newValue;
            } else {
                return oldValue;
            }
        };


        Map<String, GradeBO> spiderGradeMap = gradeList.stream()
                .collect(Collectors.toMap(keyMapper, x -> x,binaryOperator));

        Map<String, GradeBO> dbGradeMap = gradeListFromDb.stream()
                .collect(Collectors.toMap(keyMapper, x -> x, binaryOperator));

        List<GradeBO> resultList = spiderGradeMap.entrySet().stream()
                .map(entry -> {
                    GradeBO grade = dbGradeMap.remove(entry.getKey());
                    if (grade == null) {
                        entry.getValue().setNewGrade(true);
                        return entry.getValue();
                    }

                    if (!Objects.equals(grade, entry.getValue())) {
                        entry.getValue().setUpdate(true);
                        return entry.getValue();
                    }

                    return grade;
                }).collect(Collectors.toList());

        resultList.addAll(dbGradeMap.values());

        return resultList;
    }
}
