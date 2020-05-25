package com.hackerda.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.exception.*;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.net.HttpCookie;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UrpBaseSpider {

    final String ROOT = "http://xsurp.usth.edu.cn";
    /**
     * 登录校验
     */
    private final String CHECK = ROOT + "/j_spring_security_check";



    private final RestOperations client;
    private final CaptchaPredict captchaPredict;
    private final ICaptchaProvider<CaptchaImage> captchaProvider;
    private final IExceptionHandler exceptionHandler;
    final static AccountCookiePersist<String> cookiePersist = new MemoryCookiePersist<>();
    String account = "";


    public UrpBaseSpider(RestOperations client, CaptchaPredict captchaPredict,
                         ICaptchaProvider<CaptchaImage> captchaProvider) {
        this(client, captchaPredict, captchaProvider , null);
    }

    public UrpBaseSpider(RestOperations client, CaptchaPredict captchaPredict,
                         ICaptchaProvider<CaptchaImage> captchaProvider, IExceptionHandler exceptionHandler) {
        this.client = client;
        this.captchaPredict = captchaPredict;
        this.captchaProvider = captchaProvider;
        this.exceptionHandler = exceptionHandler;
    }



    protected void login(String account, String password){
        CaptchaImage preLoad = captchaProvider.get();
        String predict = captchaPredict.predict(preLoad);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("j_username", account);
        map.add("j_password", password);
        map.add("j_captcha", predict);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Referer","http://xsurp.usth.edu.cn/login");
        headers.set("Host", "xsurp.usth.edu.cn");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/75.0.3770.100 Safari/537.36");
        headers.set("Upgrade-Insecure-Requests", "1");
        headers.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;" +
                "q=0.8," +
                "application/signed-exchange;v=b3");
        headers.setOrigin("http://xsurp.usth.edu.cn");
        headers.set(HttpHeaders.COOKIE, cookieToString(preLoad.getCookie()));


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> data = postFormData(request, CHECK, String.class);


        if (data.getStatusCode().is3xxRedirection()) {
            List<String> locationList = data.getHeaders().get(HttpHeaders.LOCATION);
            if (locationList != null && !locationList.isEmpty()) {
                String location = locationList.get(0);
                if (StringUtils.isEmpty(location)) {
                    cookiePersist.clearByAccount(this.account);
                    throw new UrpRequestException(CHECK, data.getStatusCodeValue(), data.getBody());
                }
            }
        }

        checkResult(data.getBody());

        cookiePersist.saveByAccount(preLoad.getCookie(), account);

    }




    <T> ResponseEntity<T> postFormData(HttpEntity<MultiValueMap<String, String>> request, String url,
                                       Class<T> resultType) {

        return client.postForEntity(url, request, resultType);

    }


    protected void checkResult(String content) {
        if (content != null) {
            RuntimeException exception = null;
            if (content.contains("badCaptcha")) {
                exception = new UrpVerifyCodeException("verify code error");
            } else if (content.contains("badCredentials")) {
                exception = new PasswordUnCorrectException("urp spider password not correct");
            } else if (content.contains("invalidSession") || content.contains("login")) {
                exception = new UrpSessionExpiredException("session expired");
            } else if (content.contains("没有完成评估")) {
                exception = new UrpEvaluationException("评估未完成无法查看个人信息");
            }

            if (exception != null) {
                if(exceptionHandler != null){
                    try {
                        exceptionHandler.handle(exception, account);
                    }catch (Throwable throwable){
                        log.error("exceptionHandler execute error", throwable);
                    }
                }

                if(!(exception instanceof UrpEvaluationException)){
                    cookiePersist.clearByAccount(account);
                }
                throw exception;
            }
        }
    }

    protected String getContent(String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookieToString(cookiePersist.getByAccount(account)));

        ResponseEntity<String> responseEntity = client.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);

        String content = responseEntity.getBody();

        checkResult(content);

        return content;
    }


    private String cookieToString(List<HttpCookie> cookieList){

        if(cookieList != null && !cookieList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (HttpCookie cookie : cookieList) {
                sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
            }

            return sb.toString();
        }
        return "";
    }

}
