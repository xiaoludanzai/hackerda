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
import java.util.List;


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

    public abstract void setCookies(List<HttpCookie> cookies);

    private void processHeaders(HttpHeaders headers) {
        final List<String> cooks = headers.get(HttpHeaders.SET_COOKIE);
        if (cooks != null && !cooks.isEmpty()) {
            cooks.stream().map(HttpCookie::parse).forEachOrdered((cook) -> {
                cook.forEach((a) -> {
                    List<HttpCookie> cookies = getCookies();
                    cookies.stream().filter(x -> a.getName().equals(x.getName())).findAny().ifPresent(cookies::remove);
                    cookies.add(a);
                });
            });
        }
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor) throws RestClientException {
        final List<HttpCookie> cookies = getCookies();

        return super.doExecute(url, method, chr -> {
            if (cookies != null) {
                StringBuilder sb = new StringBuilder();
                for (HttpCookie cookie : cookies) {
                    sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                }
                chr.getHeaders().add(HttpHeaders.COOKIE, sb.toString());
            }
            requestCallback.doWithRequest(chr);
        }, chr -> {
            processHeaders(chr.getHeaders());
            return responseExtractor.extractData(chr);
        });
    }
}
