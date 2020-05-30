package com.hackerda.spider;


import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.config.SpiderConfiguration;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;
import com.hackerda.spider.support.scheme.SchemeGradeItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpiderIntegrationTest {

    @Autowired
    private UrpSpider urpBaseSpider;
    @Autowired
    private ICaptchaProvider<CaptchaImage> captchaProvider;

    @Before
    public void login(){
        urpBaseSpider.login("2017025838", "1");
    }

    @Test
    public void testLogin(){


    }

    @Test
    public void testGetInfo(){

        UrpStudentInfo info = urpBaseSpider.getStudentInfo();
        System.out.println(info.toString());

    }

    @Test
    public void getCurrentGeneralGrade(){


        List<UrpGeneralGrade> grade = urpBaseSpider.getCurrentGeneralGrade();


        for (UrpGeneralGrade generalGrade : grade) {
            System.out.println(generalGrade);
        }
    }


    @Test
    public void getUrpCourseTimeTable(){


        UrpCourseTimeTable table = urpBaseSpider.getUrpCourseTimeTable();

        System.out.println(table);
    }


    @Test
    public void getExamTime(){


        for (UrpExamTime examTime : urpBaseSpider.getExamTime()) {
            System.out.println(examTime);
        }
    }

    @Test
    public void getSchemeGrade(){

        List<Scheme> grade = urpBaseSpider.getSchemeGrade();

        for (Scheme scheme : grade) {
            for (SchemeGradeItem item : scheme.getCjList()) {
                System.out.println(item);
            }
        }
    }


    @Test
    public void testCookieIsRight() throws InterruptedException {

        CaptchaImage image;
        while ((image = captchaProvider.get()) != null){

            String s = image.getCookie().toString();
            System.out.println(s + " " + image.isValid());
            Thread.sleep(1000L);
        }
    }

    @Configuration
    @Import(SpiderConfiguration.class)
    public static class Config{


        @Bean
        public UrpSpider urpBaseSpider(RestTemplate client,
                                           CaptchaPredict captchaPredict,
                                       ICaptchaProvider<CaptchaImage> captchaProvider, AccountCookiePersist<String> cookiePersist){
            return new UrpCommonSpider(client, captchaPredict, captchaProvider, cookiePersist);
        }

        @Bean
        public CaptchaPredict captchaPredict(RestTemplate spiderTemplate){
            return new SchoolCaptchaPredictor(spiderTemplate, "http://spider.hackerda.com/valid");
        }

    }
}
