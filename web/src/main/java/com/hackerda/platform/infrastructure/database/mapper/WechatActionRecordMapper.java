package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.WechatActionRecord;
import com.hackerda.platform.infrastructure.database.model.WechatActionRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WechatActionRecordMapper {
    long countByExample(WechatActionRecordExample example);

    int deleteByExample(WechatActionRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatActionRecord record);

    int insertSelective(WechatActionRecord record);

    List<WechatActionRecord> selectByExample(WechatActionRecordExample example);

    WechatActionRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatActionRecord record, @Param("example") WechatActionRecordExample example);

    int updateByExample(@Param("record") WechatActionRecord record, @Param("example") WechatActionRecordExample example);

    int updateByPrimaryKeySelective(WechatActionRecord record);

    int updateByPrimaryKey(WechatActionRecord record);
}