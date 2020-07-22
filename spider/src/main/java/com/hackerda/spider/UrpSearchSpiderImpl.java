package com.hackerda.spider;

import com.alibaba.fastjson.TypeReference;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import okhttp3.Request;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class UrpSearchSpiderImpl extends UrpBaseSpider implements UrpSearchSpider{

    private final String SEARCH_COURSE_INFO = ROOT + "/student/teachingResources/classCurriculum" +
            "/searchCurriculumInfo/callback";

    /**
     * 查询课程对应的时间安排
     */
    private final String COURSE_TIMETABLE = ROOT + "/student/teachingResources/courseCurriculum" +
            "/searchCurriculum/callback?planCode=%s&courseCode=%s&courseSequenceCode=%s";


    private final String TEACHER_COURSE_TIME_TABLE = ROOT + "/student/teachingResources/teacherCurriculum" +
            "/searchCurriculumInfo/callback";

    private static final TypeReference<List<List<CourseTimetableSearchResult>>> classCourseSearchResultReference
            = new TypeReference<List<List<CourseTimetableSearchResult>>>() {
    };

    public UrpSearchSpiderImpl(RestOperations client, CaptchaPredict captchaPredict,
                               ICaptchaProvider<CaptchaImage> captchaProvider) {
        super(client, captchaPredict, captchaProvider, new MemoryCookiePersist<>());
    }

    public UrpSearchSpiderImpl(RestOperations client, CaptchaPredict captchaPredict,
                               ICaptchaProvider<CaptchaImage> captchaProvider, AccountCookiePersist<String> cookiePersist) {
        super(client, captchaPredict, captchaProvider, cookiePersist);
    }


    private void login(){
        if(!hasLogin()){
            login("2014025838", "1");
        }

    }


    @Override
    public List<List<CourseTimetableSearchResult>> searchClassTimeTable(String termYear, int termOrder, String classCode) {
        login();
        String url = SEARCH_COURSE_INFO + "?planCode=%s-%s-1&classCode=%s";
        String format = String.format(url, termYear, termOrder, classCode);
        String content = getContent(format);

        return parseObject(content, classCourseSearchResultReference);
    }

    @Override
    public List<List<CourseTimetableSearchResult>> searchCourseTimetableByTeacher(String termYear, int termOrder, String teacherNumber) {
        String url = TEACHER_COURSE_TIME_TABLE + "?planCode="+termYear+"-"+termOrder+"-1&teacherNum=" + teacherNumber;
        String content = getContent(url);
        return parseObject(content, classCourseSearchResultReference);
    }

    @Override
    public List<List<CourseTimetableSearchResult>> searchCourseTimeTable(String termYear, int termOrder, String courseId, String courseOrder) {

        String url = String.format(COURSE_TIMETABLE, termYear+"-"+termOrder+"-1", courseId, courseOrder);

        String content = getContent(url);

        return parseObject(content, classCourseSearchResultReference);
    }
}
