package com.hackerda.spider;

import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.client.UrpRestTemplate;
import com.hackerda.spider.exception.UrpSessionExpiredException;
import com.hackerda.spider.predict.CaptchaPredict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SuppressWarnings({"rawtypes", "unchecked"})
public class UrpBaseSpiderTest {


    @Test
    public void checkExceptionCallBack() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(2);
        IExceptionHandler handler = e -> latch.countDown();
        UrpBaseSpider spider = new UrpBaseSpider(null, null, null, handler){


            @Override
            protected void cleanLoginInfo() {
                latch.countDown();
            }
        };

        spider.checkResult("badCredentials");

        assertTrue(latch.await(100L, TimeUnit.MILLISECONDS));


    }
}