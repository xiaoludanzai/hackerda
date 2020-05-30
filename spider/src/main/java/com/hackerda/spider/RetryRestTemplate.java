package com.hackerda.spider;

import com.hackerda.spider.cookie.AccountCookiePersist;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


/**
 * 该client 对超时的请求进行重试
 *
 * <p>
 *
 * @author JR Chan
 */
public class RetryRestTemplate extends RestTemplate {

    public RetryRestTemplate(){
    }

    public RetryRestTemplate(ClientHttpRequestFactory factory){
        super(factory);
    }

    @Override
    @Nullable
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {

        RetryTemplate template = new RetryTemplate();

        return template.execute((RetryCallback<T, RestClientException>)
                context -> RetryRestTemplate.super.doExecute(url, method, requestCallback, responseExtractor));


    }
}
