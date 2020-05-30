package com.hackerda.spider;

import com.alibaba.fastjson.TypeReference;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class UrpSearchSpiderImpl extends UrpBaseSpider implements UrpSearchSpider{

    private final String SEARCH_COURSE_INFO = ROOT + "/student/teachingResources/classCurriculum" +
            "/searchCurriculumInfo/callback";

    private static final TypeReference<List<List<CourseTimetableSearchResult>>> classCourseSearchResultReference
            = new TypeReference<List<List<CourseTimetableSearchResult>>>() {
    };


    public UrpSearchSpiderImpl(RestOperations client, CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider) {
        super(client, captchaPredict, captchaProvider, new MemoryCookiePersist<>());
    }


    private void login(){
        if(!hasLogin()){
            login("2014025838", "1");
        }

    }

    @Override
    public List<List<CourseTimetableSearchResult>> getUrpCourseTimeTableByClassCode(String termYear, int termOrder, String classCode) {
        login();
        String url = SEARCH_COURSE_INFO + "?planCode=%s-%s-1&classCode=%s";
        String format = String.format(url, termYear, termOrder, classCode);
        String content = getContent(format);

        return parseObject(content, classCourseSearchResultReference);
    }
}
