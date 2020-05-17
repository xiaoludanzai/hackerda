package com.hackerda.spider.captcha;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class PreloadCaptchaProviderTest {

    @Test
    public void get() {
    }

    @Test
    public void testPreLoad() throws InterruptedException {
        int preloadSize = 5;

        CountDownLatch latch = new CountDownLatch(preloadSize);
        new PreloadCaptchaProvider("dummy://address", 5){

            @Override
            public CaptchaImage task(){
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    return null;
                }
                latch.countDown();
                return mock(CaptchaImage.class);
            }
        };

        assert latch.await(30000L, TimeUnit.MILLISECONDS);
    }
}