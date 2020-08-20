package com.hackerda.spider;

import com.hackerda.spider.captcha.PreloadCaptchaProvider;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
import com.hackerda.spider.support.search.SearchResult;
import com.hackerda.spider.support.search.classInfo.ClassInfoSearchResult;
import com.hackerda.spider.support.search.classInfo.SearchClassInfoPost;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class UrpSearchSpiderImplTest {

    private UrpSearchSpider urpSearchSpider;

    @Before
    public void before() {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000, TimeUnit.MILLISECONDS)
                .followRedirects(false)
                .build();

        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(client);
        RestTemplate restTemplate = new RetryRestTemplate(requestFactory);
        SchoolCaptchaPredictor predictor = new SchoolCaptchaPredictor(restTemplate, "http://spider.hackerda.com/valid");
        PreloadCaptchaProvider provider = new PreloadCaptchaProvider(restTemplate, "http://xsurp.usth.edu.cn/img/captcha" +
                ".jpg");
        this.urpSearchSpider = new UrpSearchSpiderImpl(restTemplate, predictor, provider);
    }


    @Test
    public void searchClassInfo() {

        SearchClassInfoPost post = new SearchClassInfoPost();
        String start = "2018025143".substring(0, 4);
        int end = Integer.parseInt(start) + 1;
        post.setYearNum(start);
        post.setExecutiveEducationPlanNum(start + "-"+ end + "-1-1");
        List<SearchResult<ClassInfoSearchResult>> results = urpSearchSpider.searchClassInfo(post);
        System.out.println(results.size());
        for (SearchResult<ClassInfoSearchResult> result : results) {
            System.out.println(result);
        }

    }
}