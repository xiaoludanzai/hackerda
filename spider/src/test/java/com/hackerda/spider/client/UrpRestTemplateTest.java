package com.hackerda.spider.client;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.net.HttpCookie;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

public class UrpRestTemplateTest {


    @Test
    public void testHasLogin(){

        UrpRestTemplate<String> template = new UrpRestTemplate<>();
        template.setAccount("12345");
        ArrayList<HttpCookie> cookies = Lists.newArrayList(new HttpCookie("key", "value"));
        template.setCookies(cookies);

        assertThat(template.getCookies() == cookies);
        assertThat(template.hasLogin());

        template.clearLoginInfo();

        assertThat(!template.hasLogin());

    }
}