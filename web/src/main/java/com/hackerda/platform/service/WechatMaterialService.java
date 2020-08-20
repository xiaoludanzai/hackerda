package com.hackerda.platform.service;

import com.hackerda.platform.controller.vo.WechatArticleVO;
import com.hackerda.platform.infrastructure.database.dao.WechatArticleDao;
import com.hackerda.platform.infrastructure.database.mapper.WechatArticleMapper;
import com.hackerda.platform.infrastructure.database.model.WechatArticle;
import com.hackerda.platform.infrastructure.database.model.example.WechatArticleExample;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@Slf4j
public class WechatMaterialService {

    @Autowired
    private WxMpService wxProService;
    @Autowired
    private WechatArticleDao wechatArticleDao;
    @Autowired
    private WechatArticleMapper wechatArticleMapper;

    public WechatArticleVO save() {

        WxMpMaterialService materialService = wxProService.getMaterialService();
        WechatArticleVO articleVO = new WechatArticleVO();
        try {
            WxMpMaterialNewsBatchGetResult result = materialService.materialNewsBatchGet(0, 5);


            for (WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem newsItem : result.getItems()) {
                String mediaId = newsItem.getMediaId();

                Date updateTime = newsItem.getUpdateTime();
                Calendar instance = GregorianCalendar.getInstance();

                instance.set(2020, Calendar.MAY, 1);
                if(updateTime.before(instance.getTime()) ) {
                    continue;
                }
                List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = newsItem.getContent().getArticles();
                for(int x=0 ; x <articles.size() ; x++) {
                    WxMpMaterialNews.WxMpMaterialNewsArticle article = articles.get(x);
                    String thumbUrl = URLEncoder.encode(article.getThumbUrl(), StandardCharsets.UTF_8.name());
                    String url = URLEncoder.encode(article.getUrl(), StandardCharsets.UTF_8.name());

                    WechatArticle wechatArticle = new WechatArticle();
                    wechatArticle.setTitle(article.getTitle());
                    wechatArticle.setContentUrl(url);
                    wechatArticle.setCoverUrl(thumbUrl);
                    wechatArticle.setShow(true);
                    wechatArticle.setUpdateTime(updateTime);

                    wechatArticle.setMediaId(mediaId);
                    wechatArticle.setOrderSeq(x);

                    wechatArticleDao.save(wechatArticle);
                }

            }

        } catch (WxErrorException | UnsupportedEncodingException e) {
            log.info("get wechat article error", e);
        }
        return articleVO;
    }

    public void update() {
        WxMpMaterialService materialService = wxProService.getMaterialService();
        try {
            WxMpMaterialNewsBatchGetResult result = materialService.materialNewsBatchGet(0, 5);
            for (WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem newsItem : result.getItems()) {
                String mediaId = newsItem.getMediaId();

                Date updateTime = newsItem.getUpdateTime();
                Calendar instance = GregorianCalendar.getInstance();

                instance.set(2020, Calendar.MAY, 1);
                if(updateTime.before(instance.getTime()) ) {
                    continue;
                }
                List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = newsItem.getContent().getArticles();
                for(int x=0 ; x <articles.size() ; x++) {
                    WxMpMaterialNews.WxMpMaterialNewsArticle article = articles.get(x);

                    WechatArticle wechatArticle = new WechatArticle();
                    wechatArticle.setUpdateTime(updateTime);


                    WechatArticleExample example = new WechatArticleExample();
                    example.createCriteria().andMediaIdEqualTo(mediaId).andOrderSeqEqualTo(x);
                    wechatArticleMapper.updateByExampleSelective(wechatArticle, example);

                }

            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }


    public WechatArticleVO getShowArticle () {
        WechatArticleVO articleVO = new WechatArticleVO();
        for (WechatArticle article : wechatArticleDao.getShowArticle()) {

            articleVO.add(article.getTitle(), article.getCoverUrl(), article.getContentUrl(), article.getUpdateTime());
        }
        return articleVO;
    }
}
