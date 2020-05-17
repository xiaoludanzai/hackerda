package com.hackerda.spider;

import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.client.UrpRestTemplate;
import com.hackerda.spider.exception.UrpSessionExpiredException;
import com.hackerda.spider.predict.CaptchaPredict;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SuppressWarnings({"rawtypes", "unchecked"})
public class UrpBaseSpiderTest {

    @Test
    public void getStudentInfo() {
        CaptchaPredict predict = mock(CaptchaPredict.class);
        ICaptchaProvider provider = mock(ICaptchaProvider.class);

        UrpRestTemplate client = new UrpRestTemplate() {
            @Override
            public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) {
                return new ResponseEntity("expire", HttpStatus.OK);
            }
        };

        UrpBaseSpider spider = new UrpBaseSpider(client, predict, provider);

        assertThatExceptionOfType(UrpSessionExpiredException.class)
                .isThrownBy(spider::getStudentInfo);

        verify(client).setCookies(Collections.emptyList());
    }
}