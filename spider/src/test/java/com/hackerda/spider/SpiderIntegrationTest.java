package com.hackerda.spider;


import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.captcha.PreloadCaptchaProvider;
import com.hackerda.spider.client.AccountRestTemplate;
import com.hackerda.spider.client.UrpRestTemplate;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;
import com.hackerda.spider.support.scheme.SchemeGradeItem;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpiderIntegrationTest {


    @Autowired
    private UrpBaseSpider urpBaseSpider;
    @Autowired
    private ICaptchaProvider<CaptchaImage> captchaProvider;

    @Before
    public void login(){

        urpBaseSpider.login("2017025838", "2");
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
            System.out.println(s + " " + image.isEmpty());
            Thread.sleep(1000L);
        }



    }

    @Configuration
    public static class Config{

        @Bean
        public ClientHttpRequestFactory schoolRequestFactory(){
            // 创建Http请求配置参数
            RequestConfig requestConfig = RequestConfig.custom()
                    // 获取连接超时时间
                    .setConnectionRequestTimeout(2000)
                    // 请求超时时间
                    .setConnectTimeout(500)
                    // 响应超时时间
                    .setSocketTimeout(500)
                    .setExpectContinueEnabled(true)
                    .setRedirectsEnabled(false)
                    .build();
            CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            return new HttpComponentsClientHttpRequestFactory(client);
        }


        @Bean
        public AccountCookiePersist<String> accountCookiePersist(){
            return new MemoryCookiePersist<>();
        }

        @Bean
        public AccountRestTemplate<String> accountRestTemplate(AccountCookiePersist<String> accountCookiePersist, ClientHttpRequestFactory schoolRequestFactory){
            return new UrpRestTemplate<>(schoolRequestFactory, accountCookiePersist);
        }

        @Bean
        public CaptchaPredict captchaPredict(){
            return new SchoolCaptchaPredictor(new RestTemplate(), "http://spider.hackerda.com/valid");
        }

        @Bean
        public ICaptchaProvider<CaptchaImage> captchaProvider(ClientHttpRequestFactory schoolRequestFactory){
            return new PreloadCaptchaProvider( "http://xsurp.usth.edu.cn/img/captcha" +
                            ".jpg");
        }

        @Bean
        public UrpBaseSpider urpBaseSpider(AccountRestTemplate<String> client,
                                           CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider){
            return new UrpBaseSpider(client, captchaPredict, captchaProvider);
        }

    }
}
