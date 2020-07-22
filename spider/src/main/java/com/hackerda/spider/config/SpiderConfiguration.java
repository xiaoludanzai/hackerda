package com.hackerda.spider.config;

import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.CaptchaProvider;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.RetryRestTemplate;
import com.hackerda.spider.captcha.PreloadCaptchaProvider;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class SpiderConfiguration {

    @Value("${spider.timeout.request :500}")
    private int requestTimeout;
    @Value("${spider.timeout.connect :2000}")
    private int connectTimeout;
    @Value("${spider.captcha.predict}")
    private String captchaPredict;

    @Bean
    public RestTemplate spiderTemplate(){

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(requestTimeout, TimeUnit.MILLISECONDS)
                .followRedirects(false)
                .build();

        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(client);

        return new RetryRestTemplate(requestFactory);
    }

    @Bean
    public RestTemplate searchSpiderTemplate(){

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .followRedirects(false)
                .build();

        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(client);

        return new RetryRestTemplate(requestFactory);
    }

    @Bean
    public AccountCookiePersist<String> cookiePersist(){
        return new MemoryCookiePersist<>();
    }



    @Bean
    @ConditionalOnMissingBean
    public CaptchaPredict captchaPredict(RestTemplate spiderTemplate){
        return new SchoolCaptchaPredictor(spiderTemplate, captchaPredict);
    }


    @Bean
    @ConditionalOnMissingBean
    public ICaptchaProvider<CaptchaImage> captchaProvider(RestTemplate spiderTemplate){
        return new PreloadCaptchaProvider(spiderTemplate, "http://xsurp.usth.edu.cn/img/captcha" +
                ".jpg");
    }


}
