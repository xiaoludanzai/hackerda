package com.hackerda.platform.service;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.dao.GradeDao;
import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.pojo.*;
import com.hackerda.platform.pojo.constant.ErrorCode;
import com.hackerda.platform.pojo.constant.RedisKeys;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.platform.pojo.vo.GradeVo;
import com.hackerda.platform.pojo.vo.TermGradeVo;

import com.hackerda.platform.utils.DateUtils;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpEvaluationException;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.scheme.Scheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Yuki
 * @date 2019/8/1 17:05
 */
@Slf4j
@Service
public class NewGradeSearchService {

    @Resource
    private NewUrpSpiderService newUrpSpiderService;
    @Resource
    private GradeDao gradeDao;
    @Resource
    private StudentUserDao studentUserDao;
    @Value("${spider.grade.timeout: 5000}")
    private int getGradeTimeout;
    @Autowired
    private GpaRanker gpaRanker;

    private static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    private static ExecutorService gradeAutoUpdatePool = new MDCThreadPool(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "gradeSearch"));

    /**
     * 这个方法已经过时，冗余出来给还没更新小程序新版本的用户使用
     * 请使用新方法{@linkplain #getCurrentTermGrade}
     */
    @Deprecated
    public GradeSearchResult getCurrentGrade(StudentUser student) {
        List<GradeVo> currentTermGrade = getCurrentTermGrade(student);
        List<UrpGradeAndUrpCourse> result = currentTermGrade.stream()
                .map(x -> new UrpGradeAndUrpCourse()
                        .setNewGrade(new NewGrade().setUrpGrade(new UrpGrade().setScore(x.getScore())))
                        .setUrpCourse(new UrpCourse().setCourseName(x.getCourse().getName())
                                .setCredit(Double.parseDouble(x.getCourse().getCredit()))
                        )
                        .setTerm(DateUtils.getCurrentSchoolTime().getTerm())

                ).collect(Collectors.toList());

        return new GradeSearchResult(result, false);
    }

    public List<GradeDetail> getCurrentTermGradeFromSpider(StudentUser student) {
        List<UrpGeneralGrade> generalGrade = newUrpSpiderService.getCurrentGeneralGrade(student);

        return generalGrade.stream().map(urpGeneralGrade -> {

            Grade grade = new Grade()
                    .setAccount(Integer.parseInt(urpGeneralGrade.getId().getStudentNumber()))
                    .setCourseNumber(urpGeneralGrade.getId().getCourseNumber())
                    .setCourseOrder(urpGeneralGrade.getCourseSequenceNumber())
                    .setScore(urpGeneralGrade.getCourseScore() == null ? -1 : urpGeneralGrade.getCourseScore())
                    .setGradePoint(urpGeneralGrade.getGradePoint())
                    .setLevelName(urpGeneralGrade.getLevelName())
                    .setLevelPoint(urpGeneralGrade.getLevelPoint())
                    .setRank(urpGeneralGrade.getRank())
                    .setReplaceCourseNumber(urpGeneralGrade.getReplaceCourseNumber())
                    .setRemark(urpGeneralGrade.getRemark())
                    .setRetakeCourseMark(urpGeneralGrade.getRetakeCourseMark())
                    .setRetakecourseModeCode(urpGeneralGrade.getRetakeCourseModeCode())
                    .setRetakeCourseModeExplain(urpGeneralGrade.getRetakeCourseModeExplain())
                    .setUnpassedReasonCode(urpGeneralGrade.getUnPassedReasonCode())
                    .setUnpassedReasonExplain(urpGeneralGrade.getUnPassedReasonExplain())
                    .setStandardPoint(urpGeneralGrade.getStandardPoint())
                    .setTermYear(urpGeneralGrade.getId().getTermYear())
                    .setTermOrder(urpGeneralGrade.getId().getTermOrder())
                    .setExamTime(urpGeneralGrade.getId().getExamtime())
                    .setOperateTime(urpGeneralGrade.getOperateTime())
                    .setOperator(urpGeneralGrade.getOperator())
                    .setStudyHour(StringUtils.isEmpty(urpGeneralGrade.getStudyHour()) ? 0 :
                            Integer.parseInt(urpGeneralGrade.getStudyHour()))
                    .setCourseName(urpGeneralGrade.getCourseName())
                    .setCoursePropertyCode(urpGeneralGrade.getCoursePropertyCode())
                    .setCoursePropertyName(urpGeneralGrade.getCoursePropertyName())
                    .setExamTypeName(urpGeneralGrade.getExamTypeName())
                    .setExamTypeCode(urpGeneralGrade.getExamTypeCode())
                    .setCredit(urpGeneralGrade.getCredit());

            return new GradeDetail(grade, null);

        }).collect(Collectors.toList());

    }


    /**
     * 这个方法是提供给前端使用，当抓取超时或者错误得时候会从数据库中读取数据，能保证一个有返回
     *
     * @param student 学生实体
     * @return 学生成绩
     */
    public List<GradeVo> getCurrentTermGrade(StudentUser student) {
        CompletableFuture<List<GradeVo>> future =
                CompletableFuture.supplyAsync(() -> getCurrentTermGradeVoSync(student), gradeAutoUpdatePool);
        List<GradeVo> gradeDetailList;
        try {
            gradeDetailList = future.get(5000L, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            if (cause instanceof PasswordUnCorrectException) {
                throw (PasswordUnCorrectException) cause;
            }

            List<GradeVo> gradeVoList =
                    gradeDao.getCurrentTermGradeByAccount(student.getAccount())
                            .stream()
                            .map(Grade::toVo)
                            .sorted()
                            .collect(Collectors.toList());;

            if (cause instanceof UrpEvaluationException) {
                if (gradeVoList.isEmpty()) {
                    throw (UrpEvaluationException) cause;
                }
                gradeVoList.forEach(x -> x.setErrorCode(ErrorCode.Evaluation_ERROR.getErrorCode()).setMsg(cause.getMessage()));
            } else {
                log.error("get grade error", cause);
            }

            return gradeVoList;
        } catch (InterruptedException | TimeoutException e) {
            return gradeDao.getCurrentTermGradeByAccount(student.getAccount())
                    .stream()
                    .map(Grade::toVo)
                    .sorted()
                    .collect(Collectors.toList());
        }

        return gradeDetailList;
    }


    /**
     * 这个方法主要得主要目的是为了提供给关注抓取错误，而对返回结果时间不敏感的调用方使用
     *
     * @param student 学生实体
     * @return 学生成绩
     */
    public List<GradeVo> getCurrentTermGradeVoSync(StudentUser student) {

        return getCurrentTermGradeSync(student)
                .stream()
                .map(Grade::toVo)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Grade> getCurrentTermGradeSync(StudentUser student) {
        List<GradeDetail> gradeDetailList = getCurrentTermGradeFromSpider(student);

        List<Grade> gradeList = gradeDetailList.stream()
                .map(GradeDetail::getGrade)
                .collect(Collectors.toList());
        // 检查哪些是新的成绩数据
        List<Grade> updateList = checkUpdate(student, gradeList);
        // 新的数据插入，原有得数据更新
        saveUpdateGrade(updateList);


        return updateList;
    }

    public GradeResultVo getGrade(String account, String password) {
        return getGrade(account);

    }

    public GradeResultVo getGrade(String account) {
        StudentUser student = studentUserDao.selectStudentByAccount(Integer.parseInt(account));
        return getGrade(student);
    }

    /**
     * 获取所有成绩
     *
     * 为了减少不必要的抓取
     *
     * @param student 学生实体
     * @return 所有的成绩数据
     */
    public GradeResultVo getGrade(StudentUser student) {
        GradeResultVo resultVo;

        CompletableFuture<List<Grade>> currentFuture =
                CompletableFuture.supplyAsync(() -> getCurrentTermGradeSync(student), gradeAutoUpdatePool);

        CompletableFuture<List<Grade>> schemeFuture =
                CompletableFuture.supplyAsync(() -> getSchemeGrade(student), gradeAutoUpdatePool);

        CompletableFuture<List<Grade>> completableFuture = currentFuture.thenCombine(schemeFuture,
                (x, y) -> {
                    x.addAll(y);
                    return x;
                });

        try {
            List<Grade> gradeList = completableFuture.get(getGradeTimeout, TimeUnit.MILLISECONDS);
            resultVo = new GradeResultVo(gradeList);
        } catch (InterruptedException e) {
            resultVo = new GradeResultVo(gradeDao.getGradeByAccount(student.getAccount()));
            resultVo.setErrorCode(ErrorCode.SYSTEM_ERROR.getErrorCode()).setMessage(e.getMessage());
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof PasswordUnCorrectException) {
                throw (PasswordUnCorrectException) cause;
            }
            if (cause instanceof UrpEvaluationException) {
                throw (UrpEvaluationException) cause;
            } else {
                resultVo = new GradeResultVo(gradeDao.getGradeByAccount(student.getAccount()));
                resultVo.setErrorCode(ErrorCode.SYSTEM_ERROR.getErrorCode());
                resultVo.setMessage(cause.getMessage());
                log.error("get grade error", cause);
            }

        } catch (TimeoutException e) {
            resultVo = new GradeResultVo(gradeDao.getGradeByAccount(student.getAccount()));
            resultVo.setErrorCode(ErrorCode.READ_TIMEOUT.getErrorCode()).setMessage("抓取超时");
        }

        resultVo.gpaRank(student, gpaRanker);

        return resultVo;
    }

    public List<Grade> getSchemeGrade(StudentUser student) {

        List<Grade> gradeList = getSchemeGradeFromSpider(student);

        gradeDao.insertBatch(gradeList);

        return gradeList;

    }

    public List<Grade> getSchemeGradeFromSpider(StudentUser student){
        return newUrpSpiderService.getSchemeGrade(student)
                .stream()
                .map(Scheme::getCjList)
                .flatMap(Collection::stream)
                .map(item -> new Grade()
                        .setCredit(Double.parseDouble(item.getCredit()))
                        .setExamTypeCode(item.getExamTypeCode())
                        .setCoursePropertyName(item.getCourseAttributeName())
                        .setCoursePropertyCode(item.getCourseAttributeCode())
                        .setCourseName(item.getCourseName())
                        .setCourseNumber(item.getId().getCourseNumber())
                        .setCourseOrder(item.getId().getCourseSequenceNumber())
                        .setOperator(item.getOperator())
                        .setOperateTime(item.getOperatingTime())
                        .setScore(Double.parseDouble(item.getScore()))
                        .setExamTime(item.getExamTime())
                        .setAccount(Integer.parseInt(item.getId().getStudentId()))
                        .setExamTypeCode(item.getExamTypeCode())
                        .setExamTypeName(item.getExamTypeName())
                        .setLevelName(item.getGradeName())
                        .setStandardPoint(item.getStandardScore())
                        .setLevelPoint(Integer.toString(item.getGradeScore()))
                        .setGradePoint(Double.parseDouble(item.getGradePointScore()))
                        .setTermYear(item.getTermYear())
                        .setTermOrder(item.getTermCode())
                        .setStudyHour(item.getCycle() == null ? 0 : Integer.parseInt(item.getCycle()))
                        .setRank(0))
                .collect(Collectors.toList());
    }

    /**
     * 返回新一个新成绩的list，旧的的成绩会被过滤
     */
    public List<Grade> checkUpdate(StudentUser student, List<Grade> gradeList) {
        List<Grade> gradeListFromDb = gradeDao.getCurrentTermGradeByAccount(student.getAccount());
        // 如果数据库之前没有保存过该学号成绩，则直接返回抓取结果
        if (CollectionUtils.isEmpty(gradeListFromDb)) {
            gradeList.forEach(x-> x.setUpdate(true));
            return gradeList;
        }
        Function<Grade, String> keyMapper = grade -> grade.getCourseNumber() + grade.getCourseOrder();

        BinaryOperator<Grade> binaryOperator = (oldValue, newValue) -> {
            if (oldValue.getScore() == -1) {
                return newValue;
            } else {
                return oldValue;
            }
        };

        // 这个逻辑是处理教务网同一个课程返回两个结果，那么选择有成绩的结果
        Map<String, Grade> spiderGradeMap = gradeList.stream()
                .collect(Collectors.toMap(keyMapper, x -> x,binaryOperator));

        Map<String, Grade> dbGradeMap = gradeListFromDb.stream()
                .collect(Collectors.toMap(keyMapper, x -> x, binaryOperator));

        List<Grade> resultList = spiderGradeMap.entrySet().stream()
                .map(entry -> {
                    Grade grade = dbGradeMap.remove(entry.getKey());
                    if (grade == null) {
                        entry.getValue().setUpdate(true);
                        return entry.getValue();
                    }

                    if (!Objects.equals(grade, entry.getValue())) {
                        entry.getValue().setUpdate(true);
                        entry.getValue().setId(grade.getId());
                        return entry.getValue();
                    }

                    return grade;
                }).collect(Collectors.toList());

        resultList.addAll(dbGradeMap.values());

        return resultList;
    }


    public void saveUpdateGrade(List<Grade> gradeList) {
        List<Grade> updateList = gradeList.stream()
                .filter(grade -> grade.getId() != null && grade.isUpdate())
                .collect(Collectors.toList());

        List<Grade> newList = gradeList.stream()
                .filter(grade -> grade.getId() == null)
                .collect(Collectors.toList());
        for (Grade grade : updateList) {
            gradeDao.updateByPrimaryKeySelective(grade);
        }
        gradeDao.insertBatch(newList);
    }


    public static String gradeListToText(List<UrpGradeAndUrpCourse> studentGrades) {
        StringBuilder buffer = new StringBuilder();
        if (studentGrades.size() == 0) {
            buffer.append("尚无本学期成绩");
        } else {
            //因为查询的都是同学期的，所以取第一个元素即可
            Term term = studentGrades.get(0).getTerm();
            buffer.append("- - - - - - - - - - - - - -\n");
            buffer.append("|").append(term.getTermCode()).append("学年，").append(term.getTermName()).append("|\n");
            for (UrpGradeAndUrpCourse urpGradeAndUrpCourse : studentGrades) {
                Double grade = urpGradeAndUrpCourse.getNewGrade().getUrpGrade().getScore();
                //如果分数为空，就直接跳过当前元素
                if (Objects.isNull(grade)) {
                    continue;
                }
                buffer.append("考试名称：").append(urpGradeAndUrpCourse.getUrpCourse().getCourseName()).append("\n")
                        .append("成绩：").append(grade == -1 ? "" : decimalFormat.format(grade)).append("   学分：")
                        .append((decimalFormat.format(urpGradeAndUrpCourse.getUrpCourse().getCredit()))).append("\n\n");
            }
            buffer.append("- - - - - - - - - - - - - -");
        }
        return buffer.toString();
    }

}
