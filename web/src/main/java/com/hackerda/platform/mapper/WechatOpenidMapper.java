package com.hackerda.platform.mapper;

import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.example.WechatOpenidExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WechatOpenidMapper {
    int countByExample(WechatOpenidExample example);

    int deleteByExample(WechatOpenidExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatOpenid record);

    int insertSelective(WechatOpenid record);

    List<WechatOpenid> selectByExample(WechatOpenidExample example);

    WechatOpenid selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatOpenid record, @Param("example") WechatOpenidExample example);

    int updateByExample(@Param("record") WechatOpenid record, @Param("example") WechatOpenidExample example);

    int updateByPrimaryKeySelective(WechatOpenid record);

    int updateByPrimaryKey(WechatOpenid record);

    //添加判断是否绑定的方法仿照OpenidPlusMapper编码
    int isOpenidBind(String openid);
    // 仿照OpenidPlusMapper编码
    int openidUnbind(String openid);
    //后添加方法仿照OpenidPlusMapper里面的getAllOpenidsFromOneClass方法，用于OpenidService
    List<String> getAllOpenidsFromOneClass(@Param("classId") int classId, @Param("openid") String openid);
}