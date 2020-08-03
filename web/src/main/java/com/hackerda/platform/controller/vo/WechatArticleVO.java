package com.hackerda.platform.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
public class WechatArticleVO {

    private int count;

    private final List<WechatArticleItemVO> articleList = new ArrayList<>(0);


    public void add(String title, String thumbUrl, String url, Date updateTime) {
        count += 1;
        articleList.add(new WechatArticleItemVO(title, thumbUrl, url, updateTime));
    }

    @AllArgsConstructor
    @Getter
    private static class WechatArticleItemVO {

        private final String title;

        private final String thumbUrl;

        private final String url;

        private final Date updateTime;
    }
}
