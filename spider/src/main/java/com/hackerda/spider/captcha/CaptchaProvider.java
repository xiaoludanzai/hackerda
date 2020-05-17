package com.hackerda.spider.captcha;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


/**
 * 验证码生产者，允许用预加载的方式来获取验证码以减少网络开销
 * 也可以以同步的方式使用当前线程获取验证码
 *
 * @author JR Chan
 */
public class CaptchaProvider implements ICaptchaProvider<CaptchaImage> {
    Logger logger = LoggerFactory.getLogger(CaptchaProvider.class);

    private final RestOperations restOperations;

    private final String captchaUrl;

    public CaptchaProvider(RestOperations restOperations, String captchaUrl) {
        this.restOperations = restOperations;
        this.captchaUrl = captchaUrl;
    }


    @Override
    public CaptchaImage get() {
        return task();
    }

    @Override
    public CaptchaImage get(long timeOut, TimeUnit timeUnit) {
        CompletableFuture<CaptchaImage> future = CompletableFuture.supplyAsync(this::task);
        try {
            return future.get(timeOut, timeUnit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.warn("get captcha error", e);
            future.cancel(true);
            return null;
        }
    }


    protected CaptchaImage task() {

        ResponseEntity<byte[]> entity = restOperations.getForEntity(captchaUrl, byte[].class);

        byte[] body = entity.getBody();

        if (body == null) {
            throw new RuntimeException("image is null");
        }

        HttpHeaders headers = entity.getHeaders();

        return new CaptchaImage(body, getCookieFromHeader(headers));

    }

    private List<HttpCookie> getCookieFromHeader(HttpHeaders headers) {
        List<String> cookieStrList = headers.get(HttpHeaders.SET_COOKIE);
        if (cookieStrList == null) {
            return Collections.emptyList();
        }

        return cookieStrList.stream().map(HttpCookie::parse)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        CaptchaProvider provider = new CaptchaProvider(new RestTemplate(), "http://xsurp.usth.edu.cn/img/captcha.jpg");

        CaptchaImage image = provider.get();

        System.out.println(image);

        image.write("");
    }

}
