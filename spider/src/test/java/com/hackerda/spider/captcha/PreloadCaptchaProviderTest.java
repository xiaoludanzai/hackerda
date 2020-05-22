package com.hackerda.spider.captcha;

import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class PreloadCaptchaProviderTest {


    @Test
    public void testPreLoad() throws InterruptedException {
        int preloadSize = 5;

        AtomicInteger integer = new AtomicInteger(0);
        PreloadCaptchaProvider provider = new PreloadCaptchaProvider("dummy://address", preloadSize) {

            @Override
            public CaptchaImage task() {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                integer.getAndIncrement();
                return new CaptchaImage(new byte[1], Collections.emptyList()) {
                    @Override
                    public boolean isValid() {
                        return false;
                    }
                };
            }
        };
        Thread.sleep(1000 * 10L);
        assertEquals(integer.get(), preloadSize+ provider.getProducerCount());
    }
}