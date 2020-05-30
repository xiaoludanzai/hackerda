
package com.hackerda.platform.config;


import com.hackerda.spider.IExceptionHandler;
import com.hackerda.spider.UrpBaseSpider;
import com.hackerda.spider.UrpCommonSpider;
import com.hackerda.spider.UrpSpider;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.config.SpiderConfiguration;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhouqinglai
 * @version version
 * @title BasicsConfig
 * @desc  基础配置
 * @date: 2019年05月03日
 */
@Configuration
@Import(SpiderConfiguration.class)
public class BasicsConfig {

    @Autowired
    private IExceptionHandler spiderExceptionHandler;

    @Bean
    @Scope("prototype")
    public UrpSpider urpBaseSpider(RestTemplate client,
                                   CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider,
                                   AccountCookiePersist<String> cookiePersist){

        return new UrpCommonSpider(client, captchaPredict, captchaProvider, cookiePersist, spiderExceptionHandler);
    }

}