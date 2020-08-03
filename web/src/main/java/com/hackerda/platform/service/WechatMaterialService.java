package com.hackerda.platform.service;

import com.hackerda.platform.controller.vo.WechatArticleVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class WechatMaterialService {

    @Autowired
    private WxMpService wxProService;

    public WechatArticleVO getArticle() {


        WxMpMaterialService materialService = wxProService.getMaterialService();
        WechatArticleVO articleVO = new WechatArticleVO();
        try {
            WxMpMaterialNewsBatchGetResult result = materialService.materialNewsBatchGet(0, 5);

            for (WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem newsItem : result.getItems()) {

                Date updateTime = newsItem.getUpdateTime();
                WxMpMaterialNews.WxMpMaterialNewsArticle article = newsItem.getContent().getArticles().get(0);
                String thumbUrl = article.getThumbUrl();
                String title = article.getTitle();
                String url = URLEncoder.encode(article.getUrl(), StandardCharsets.UTF_8.name());

                articleVO.add(title, thumbUrl, url, updateTime);

            }

        } catch (WxErrorException | UnsupportedEncodingException e) {
            log.info("get wechat article error", e);
        }
        return articleVO;
    }
}
