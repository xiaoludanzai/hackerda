package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.WechatArticle;
import com.hackerda.platform.infrastructure.database.model.example.WechatArticleExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WechatArticleMapper {
    long countByExample(WechatArticleExample example);

    int deleteByExample(WechatArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatArticle record);

    int insertSelective(WechatArticle record);

    List<WechatArticle> selectByExample(WechatArticleExample example);

    WechatArticle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatArticle record, @Param("example") WechatArticleExample example);

    int updateByExample(@Param("record") WechatArticle record, @Param("example") WechatArticleExample example);

    int updateByPrimaryKeySelective(WechatArticle record);

    int updateByPrimaryKey(WechatArticle record);
}