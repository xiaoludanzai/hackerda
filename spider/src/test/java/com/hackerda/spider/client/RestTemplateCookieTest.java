package com.hackerda.spider.client;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestTemplateCookieTest {


    @Test
    public void testCookieSetCorrect() throws URISyntaxException {

        AtomicBoolean getCookie = new AtomicBoolean(false);
        AtomicBoolean setCookie = new AtomicBoolean(false);

        RestTemplateWithCookie template = new RestTemplateWithCookie() {
            @Override
            public List<HttpCookie> getCookies() {
                getCookie.set(true);
                return null;
            }

            @Override
            public void setCookiesFromResponse(HttpHeaders headers) {
                setCookie.set(true);
            }

            @Override
            protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
                // stud real request
                ClientHttpRequest request = mock(ClientHttpRequest.class);
                when(request.execute()).thenReturn(mock(ClientHttpResponse.class));
                return request;
            }

            @Override
            protected void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
                // do nothing
            }
        };

        AtomicBoolean doRequest = new AtomicBoolean(false);
        AtomicBoolean doResponse = new AtomicBoolean(false);
        template.execute(new URI("dummy://address"), HttpMethod.TRACE, request -> {
            doRequest.set(true);
        }, response -> {
            doResponse.set(true);
            return null;
        });

        // check the cookie get and set
        assertThat(doRequest.get() && getCookie.get());
        assertThat(doResponse.get() && setCookie.get());

    }


}