
package com.hackerda.platform.config;


import com.hackerda.spider.*;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.config.SpiderConfiguration;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.predict.CaptchaPredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean
    @Scope("prototype")
    public UrpSpider urpBaseSpider(RestTemplate spiderTemplate,
                                   CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider,
                                   AccountCookiePersist<String> cookiePersist, IExceptionHandler spiderExceptionHandler){

        return new UrpCommonSpider(spiderTemplate, captchaPredict, captchaProvider, cookiePersist, spiderExceptionHandler);
    }

    @Bean
    public UrpSearchSpider uepSearchSpider(RestTemplate searchSpiderTemplate,
                                           CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider,
                                           AccountCookiePersist<String> cookiePersist){

        return new UrpSearchSpiderImpl(searchSpiderTemplate, captchaPredict, captchaProvider, cookiePersist);
    }

}