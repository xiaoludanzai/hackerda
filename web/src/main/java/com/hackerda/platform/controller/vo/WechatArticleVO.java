package com.hackerda.platform.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@ToString
@Getter
public class WechatArticleVO {

    private int count;

    private final List<WechatArticleItemVO> articleList = new ArrayList<>(0);


    public void add(String title, String thumbUrl, String url, Date updateTime) {
        count += 1;
        articleList.add(new WechatArticleItemVO(title, thumbUrl, url, updateTime));

        articleList.sort(Comparator.comparing(WechatArticleItemVO::getUpdateTime));

        Collections.reverse(articleList);

    }

    @AllArgsConstructor
    @Getter
    @ToString
    private static class WechatArticleItemVO {

        private final String title;

        private final String thumbUrl;

        private final String url;

        private final Date updateTime;
    }
}
