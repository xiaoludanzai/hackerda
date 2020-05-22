package com.hackerda.spider.captcha;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class CaptchaProviderTest {

    @Test
    public void get() {

        RestTemplate template = new RestTemplate() {

            @Override
            @Nullable
            public <T> T execute(URI url, HttpMethod method, @Nullable RequestCallback requestCallback,
                                 @Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {

                return doExecute(url, method, requestCallback, responseExtractor);
            }
        };

    }
}