package com.hackerda.spider;

import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.config.SpiderConfiguration;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UrpCommonSpiderTest {


    @Autowired
    private RestTemplate client;
    @Autowired
    private CaptchaPredict captchaPredict;
    @Autowired
    private ICaptchaProvider<CaptchaImage> captchaProvider;



    @Test
    public void login() {

        MemoryCookiePersist<String> cookiePersist = spy(new MemoryCookiePersist<>());
        new UrpCommonSpider(client, captchaPredict, captchaProvider, cookiePersist);

    }



    @Configuration
    @Import(SpiderConfiguration.class)
    public static class Config{

        @Bean
        public CaptchaPredict captchaPredict(RestTemplate spiderTemplate){
            return new SchoolCaptchaPredictor(spiderTemplate);
        }

    }
}