package com.hackerda.platform.config;

import com.hackerda.platform.controller.interceptor.TraceIDInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author junrong.chen
 * @date 2018/10/13
 */
@Configuration
@Slf4j
public class WebAppConfigurer implements WebMvcConfigurer {



	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIDInterceptor()).addPathPatterns("/**");

	}

}
