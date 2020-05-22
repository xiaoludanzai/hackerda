package com.hackerda.platform.service;

import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.spider.NewUrpSpider;
import com.hackerda.platform.spider.newmodel.SearchResult;
import com.hackerda.platform.spider.newmodel.evaluation.EvaluationPagePost;
import com.hackerda.platform.spider.newmodel.evaluation.EvaluationPost;
import com.hackerda.platform.spider.newmodel.evaluation.searchresult.TeachingEvaluation;
import com.hackerda.platform.spider.newmodel.searchclass.ClassInfoSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.CourseTimetableSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.SearchClassInfoPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomResult;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchResultWrapper;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCoursePost;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCourseResult;
import com.hackerda.platform.spider.newmodel.searchteacher.SearchTeacherPost;
import com.hackerda.platform.spider.newmodel.searchteacher.SearchTeacherResult;
import com.hackerda.platform.utils.DESUtil;
import com.hackerda.spider.UrpBaseSpider;
import com.hackerda.spider.UrpSpider;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpException;
import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 第一次登录成功后，将学号对应session的cookie持久化
 * 如果需要不使用验证码登录，使用之前需要校验该账号是否有可用的cookie
 *
 * @author junrong.chen
 * @date 2019/7/18
 */
@Slf4j
@Service
public class NewUrpSpiderService {
    @Resource
    private StudentUserDao studentDao;
    @Resource
    private OpenIdService openIdService;
    @Value("${student.password.salt}")
    private String key;
    @Autowired
    private UrpSpider baseSpider;

    /**
     * 这个方法只有基本得成绩信息  包括相信成绩信息的抓取使用{@see #getCurrentTermGrade()}
     */
    @Retryable(value = UrpException.class, maxAttempts = 3)
    List<UrpGeneralGrade> getCurrentGeneralGrade(StudentUser student) {
        baseSpider.login(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
        return baseSpider.getCurrentGeneralGrade();
    }


    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<SearchResult<ClassInfoSearchResult>> getClassInfoSearchResult(SearchClassInfoPost searchClassInfoPost) {

        NewUrpSpider spider = getSpider("2014025838", "1");
        return spider.getClassInfoSearchResult(searchClassInfoPost);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<List<CourseTimetableSearchResult>> searchClassTimeTable(String termYear, int termOrder, String classCode) {
        NewUrpSpider spider = getSpider("2014025838", "1");
        return spider.getUrpCourseTimeTableByClassCode(termYear, termOrder, classCode);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<SearchResult<SearchTeacherResult>> searchTeacherInfo(SearchTeacherPost searchTeacherPost) {
        NewUrpSpider spider = getSpider("2014025838", "1");;
        return spider.searchTeacherInfo(searchTeacherPost);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<List<CourseTimetableSearchResult>> searchCourseTimetableByTeacher(String teacherNumber) {
        NewUrpSpider spider = getSpider("2014025838", "1");
        return spider.getUrpCourseTimeTableByTeacherAccount(teacherNumber);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<SearchResultWrapper<SearchClassroomResult>> searchClassroomInfo(SearchClassroomPost searchClassroomPost) {
        NewUrpSpider spider = getSpider("2014025838", "1");
        return spider.searchClassroomInfo(searchClassroomPost);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public SearchResult<SearchCourseResult> searchCourseInfo(SearchCoursePost searchCoursePost) {
        NewUrpSpider spider = getSpider("2014025838", "1");
        return spider.searchCourseInfo(searchCoursePost);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<List<CourseTimetableSearchResult>> searchCourseTimeTable(Course course) {
        NewUrpSpider spider = getSpider("2014025838", "1");
        return spider.getUrpCourseTimeTableByCourse(course);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public void checkStudentPassword(String account, String password) {
        baseSpider.login(account, password);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public UrpCourseTimeTable getUrpCourseTimeTable(StudentUser student) {
        baseSpider.login(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
        return baseSpider.getUrpCourseTimeTable();
    }


    @Retryable(value = UrpException.class, maxAttempts = 3)
    public TeachingEvaluation searchTeachingEvaluationInfo(StudentUser student) {
        NewUrpSpider spider = getSpider(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
        return spider.searchTeachingEvaluationInfo();
    }


    @Retryable(value = UrpException.class, maxAttempts = 3)
    public void evaluate(StudentUser student, EvaluationPost evaluationPost) {
        NewUrpSpider spider = getSpider(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
        spider.evaluation(evaluationPost);
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public String getEvaluationToken(StudentUser student, EvaluationPagePost evaluationPagePost) {
        NewUrpSpider spider = getSpider(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
        return spider.getEvaluationToken(evaluationPagePost);
    }


    /**
     * 获取学生信息
     */
    @Retryable(value = UrpException.class, maxAttempts = 3)
    public StudentUser getStudentUserInfo(String account, String password) {
        baseSpider.login(account, password);
        UrpStudentInfo urpStudentInfo = baseSpider.getStudentInfo();

        StudentUser studentUser = new StudentUser();
        //首先将student原有字段赋给studentUser
        studentUser.setAccount(urpStudentInfo.getAccount());
        studentUser.setPassword(DESUtil.encrypt(password, account + key));
        studentUser.setName(urpStudentInfo.getName());
        studentUser.setSex(urpStudentInfo.getSex());
        studentUser.setEthnic(urpStudentInfo.getEthnic());
        studentUser.setIsCorrect(true);

        studentUser.setAcademyName(urpStudentInfo.getAcademy());
        studentUser.setSubjectName(urpStudentInfo.getMajor());
        studentUser.setClassName(urpStudentInfo.getClassname());
        return studentUser;
    }


    /**
     * 考试安排
     *
     * @return
     */
    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<UrpExamTime> getExamTime(String account, String password) {
        baseSpider.login(account, password);

        return baseSpider.getExamTime();
    }

    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<UrpExamTime> getExamTime(StudentUser student) {
        return getExamTime(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
    }


    @Retryable(value = UrpException.class, maxAttempts = 3)
    public List<Scheme> getSchemeGrade(StudentUser student) {
        baseSpider.login(student.getAccount().toString(), student.getEnablePassword(student.getAccount().toString()+key));
        return baseSpider.getSchemeGrade();
    }


    private NewUrpSpider getSpider(String account, String password) {
        try {
            return new NewUrpSpider(account, password);
        } catch (PasswordUnCorrectException e) {
            studentDao.updatePasswordUnCorrect(Integer.parseInt(account));
            openIdService.openIdUnbindAllPlatform(Integer.parseInt(account));
            throw e;
        }

    }
}
