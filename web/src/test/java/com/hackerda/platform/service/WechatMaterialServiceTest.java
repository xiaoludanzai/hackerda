package com.hackerda.platform.service;

import com.hackerda.platform.controller.vo.WechatArticleVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatMaterialServiceTest {

    @Autowired
    private WechatMaterialService wechatMaterialService;

    @Test
    public void getArticle() {

        WechatArticleVO showArticle = wechatMaterialService.getShowArticle();

        System.out.println(showArticle);
    }
}