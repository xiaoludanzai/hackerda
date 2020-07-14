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

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
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

    private static ExecutorService gradeAutoUpdatePool = new MDCThreadPool(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "gradeSearch"));


    @Override
    public void save(List<GradeBO> gradeList) {
        List<Grade> list = gradeList.stream().map(x -> gradeAdapter.toDO(x)).collect(Collectors.toList());
        gradeDao.insertBatch(list);
    }

    @Override
    public void update(List<GradeBO> gradeList) {
        List<Grade> list = gradeList.stream().map(x -> gradeAdapter.toDO(x)).collect(Collectors.toList());

        for (Grade grade : list) {
            gradeDao.updateByPrimaryKeySelective(grade);
        }
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

        List<Grade> gradeList;
        Exception exception = null;
        try {
            gradeList = completableFuture.get(getGradeTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            gradeList =
                    gradeDao.getGradeByAccount(student.getAccount());
            exception = e;
        }

        List<TermGradeBO> termGradeList =
                gradeToTermGradeList(gradeList.stream()
                        .map(x-> gradeAdapter.toBO(x))
                        .collect(Collectors.toList()));

        if(exception != null){
            handleException(termGradeList, exception);
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

}
