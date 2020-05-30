package com.hackerda.spider;

import com.google.common.collect.Lists;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpRequestException;
import com.hackerda.spider.predict.CaptchaPredict;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.HttpCookie;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class UrpBaseSpiderTest {


    @Test
    public void testLoginSuccess(){

        CaptchaPredict predict = mock(CaptchaPredict.class);
        ICaptchaProvider<CaptchaImage> provider = mock(ICaptchaProvider.class);
        MemoryCookiePersist<String> cookiePersist = spy(new MemoryCookiePersist<>());

        when(provider.get())
                .thenReturn(new CaptchaImage(new byte[0], Lists.newArrayList(new HttpCookie("j_session",
                        "session_address"))));
        UrpBaseSpider spider = new UrpBaseSpider(null, predict, provider, cookiePersist) {

            @Override
            protected <T> ResponseEntity<T> postFormData(HttpEntity<MultiValueMap<String, String>> request, String url, Class<T> resultType) {

                String cookie = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.COOKIE)).get(0);
                assertThat(cookie.equals("j_session=session_address;"));

                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("Location", "dummy");
                return new ResponseEntity<>(map, HttpStatus.FOUND);
            }
        };

        spider.login("dummy", "dummy");

        assertThat(spider.hasLogin());

        verify(cookiePersist, times(1)).saveByAccount(anyList(), eq("dummy"));
    }


    @Test
    public void testFailWith2xx(){

        CaptchaPredict predict = mock(CaptchaPredict.class);
        ICaptchaProvider<CaptchaImage> provider = mock(ICaptchaProvider.class);
        MemoryCookiePersist<String> cookiePersist = new MemoryCookiePersist<>();

        when(provider.get())
                .thenReturn(new CaptchaImage(new byte[0], Lists.newArrayList(new HttpCookie("j_session",
                        "session_address"))));
        UrpBaseSpider spider = new UrpBaseSpider(null, predict, provider, cookiePersist) {

            @Override
            protected <T> ResponseEntity<T> postFormData(HttpEntity<MultiValueMap<String, String>> request, String url, Class<T> resultType) {


                return new ResponseEntity<>(HttpStatus.OK);
            }
        };

        assertThatExceptionOfType(UrpRequestException.class)
                .isThrownBy(() -> spider.login("dummy", "dummy"));

        assertThat(!spider.hasLogin());


    }


    @Test
    public void testFailWithEmptyLocation(){

        CaptchaPredict predict = mock(CaptchaPredict.class);
        ICaptchaProvider<CaptchaImage> provider = mock(ICaptchaProvider.class);
        MemoryCookiePersist<String> cookiePersist = new MemoryCookiePersist<>();

        when(provider.get())
                .thenReturn(new CaptchaImage(new byte[0], Lists.newArrayList(new HttpCookie("j_session",
                        "session_address"))));
        UrpBaseSpider spider = new UrpBaseSpider(null, predict, provider, cookiePersist) {

            @Override
            protected <T> ResponseEntity<T> postFormData(HttpEntity<MultiValueMap<String, String>> request, String url, Class<T> resultType) {

                return new ResponseEntity<>(HttpStatus.FOUND);
            }
        };

        assertThatExceptionOfType(UrpRequestException.class)
                .isThrownBy(() -> spider.login("dummy", "dummy"));

        assertThat(!spider.hasLogin());

    }



    @Test
    public void testPasswordUnCorrect(){

        CaptchaPredict predict = mock(CaptchaPredict.class);
        ICaptchaProvider<CaptchaImage> provider = mock(ICaptchaProvider.class);
        MemoryCookiePersist<String> cookiePersist = new MemoryCookiePersist<>();
        IExceptionHandler handler = mock(IExceptionHandler.class);

        cookiePersist.saveByAccount(Lists.newArrayList(new HttpCookie("j_session",
                "session_address")), "dummy");

        when(provider.get())
                .thenReturn(new CaptchaImage(new byte[0], Lists.newArrayList(new HttpCookie("j_session",
                        "session_address"))));
        UrpBaseSpider spider = new UrpBaseSpider(null, predict, provider, cookiePersist, handler);


        spider.setAccount("dummy");

        assertThat(spider.hasLogin());

        assertThatExceptionOfType(PasswordUnCorrectException.class)
                .isThrownBy(() -> spider.checkResult("http://xsurp.usth.edu.cn/login?errorCode=badCredentials"));

        assertThat(!spider.hasLogin());
        verify(handler, times(1)).handle(anyObject(), eq("dummy"));

    }



}