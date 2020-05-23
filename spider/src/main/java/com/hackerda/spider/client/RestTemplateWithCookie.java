package com.hackerda.spider.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 这个实现扩展了RestTemplate，允许每次请求时时主动设置cookie {@link RestTemplateWithCookie#getCookies}
 * 接受到响应时记录下cookie
 *
 * @author JR Chan
 */
public abstract class RestTemplateWithCookie extends RestTemplate {

    public RestTemplateWithCookie() {

    }

    public RestTemplateWithCookie(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }


    public abstract List<HttpCookie> getCookies();

    public abstract void setCookiesFromResponse(HttpHeaders headers);

    List<HttpCookie> processHeadersCookie(HttpHeaders headers) {
        final List<String> cooks = headers.get(HttpHeaders.SET_COOKIE);
        if (cooks != null && !cooks.isEmpty()) {
            return cooks.stream().map(HttpCookie::parse)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor) throws RestClientException {


        return super.doExecute(url, method, chr -> {
            final List<HttpCookie> cookies = getCookies();
            if (cookies != null) {
                StringBuilder sb = new StringBuilder();
                for (HttpCookie cookie : cookies) {
                    sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                }
                chr.getHeaders().add(HttpHeaders.COOKIE, sb.toString());
            }
            requestCallback.doWithRequest(chr);
        }, chr -> {
            setCookiesFromResponse(chr.getHeaders());
            return responseExtractor.extractData(chr);
        });
    }
}
