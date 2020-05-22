package com.hackerda.spider.config;

import com.hackerda.spider.UrpBaseSpider;
import com.hackerda.spider.UrpSpider;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.captcha.PreloadCaptchaProvider;
import com.hackerda.spider.client.AccountRestTemplate;
import com.hackerda.spider.client.UrpRestTemplate;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpiderConfiguration {

    @Bean
    public ClientHttpRequestFactory schoolRequestFactory(){
        // 创建Http请求配置参数
        RequestConfig requestConfig = RequestConfig.custom()
                // 获取连接超时时间
                .setConnectionRequestTimeout(2000)
                // 请求超时时间
                .setConnectTimeout(500)
                // 响应超时时间
                .setSocketTimeout(500)
                .setExpectContinueEnabled(true)
                .setRedirectsEnabled(false)
                .build();
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean
    public AccountCookiePersist<String> accountCookiePersist(){
        return new MemoryCookiePersist<>();
    }

    @Bean
    public AccountRestTemplate<String> accountRestTemplate(AccountCookiePersist<String> accountCookiePersist, ClientHttpRequestFactory schoolRequestFactory){
        return new UrpRestTemplate<>(schoolRequestFactory, accountCookiePersist);
    }

    @Bean
    public CaptchaPredict captchaPredict(){
        return new SchoolCaptchaPredictor(new RestTemplate(), "http://spider.hackerda.com/valid");
    }

    @Bean
    public ICaptchaProvider<CaptchaImage> captchaProvider(){
        return new PreloadCaptchaProvider( "http://xsurp.usth.edu.cn/img/captcha" +
                ".jpg");
    }

    @Bean
    @Scope("prototype")
    public UrpSpider urpBaseSpider(AccountRestTemplate<String> client,
                                   CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider){
        return new UrpBaseSpider(client, captchaPredict, captchaProvider);
    }
}
