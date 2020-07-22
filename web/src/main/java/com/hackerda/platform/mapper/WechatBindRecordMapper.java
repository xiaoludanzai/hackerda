package com.hackerda.platform.mapper;

import com.hackerda.platform.pojo.WechatBindRecord;
import com.hackerda.platform.pojo.example.WechatBindRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WechatBindRecordMapper {
    int countByExample(WechatBindRecordExample example);

    int deleteByExample(WechatBindRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatBindRecord record);

    int insertSelective(WechatBindRecord record);

    List<WechatBindRecord> selectByExample(WechatBindRecordExample example);

    WechatBindRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatBindRecord record, @Param("example") WechatBindRecordExample example);

    int updateByExample(@Param("record") WechatBindRecord record, @Param("example") WechatBindRecordExample example);

    int updateByPrimaryKeySelective(WechatBindRecord record);

    int updateByPrimaryKey(WechatBindRecord record);
}