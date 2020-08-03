package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.WechatArticleMapper;
import com.hackerda.platform.infrastructure.database.model.WechatArticle;
import com.hackerda.platform.infrastructure.database.model.example.WechatArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WechatArticleDao {

    @Autowired
    private WechatArticleMapper wechatArticleMapper;


    public List<WechatArticle> getShowArticle () {
        WechatArticleExample example = new WechatArticleExample();
        example.createCriteria().andShowEqualTo((byte)1);
        return wechatArticleMapper.selectByExample(example);
    }

    public int save (WechatArticle wechatArticle) {
        return wechatArticleMapper.insertSelective(wechatArticle);
    }


    public int updateUrl (int key, String url) {

        WechatArticle article = new WechatArticle();
        article.setId(key);
        article.setContentUrl(url);
        return wechatArticleMapper.updateByPrimaryKeySelective(article);
    }

}
