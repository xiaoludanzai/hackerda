package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.WechatOpenidStudentRelative;
import com.hackerda.platform.infrastructure.database.model.WechatOpenidStudentRelativeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WechatOpenidStudentRelativeMapper {
    long countByExample(WechatOpenidStudentRelativeExample example);

    int deleteByExample(WechatOpenidStudentRelativeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatOpenidStudentRelative record);

    int insertSelective(WechatOpenidStudentRelative record);

    List<WechatOpenidStudentRelative> selectByExample(WechatOpenidStudentRelativeExample example);

    WechatOpenidStudentRelative selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatOpenidStudentRelative record, @Param("example") WechatOpenidStudentRelativeExample example);

    int updateByExample(@Param("record") WechatOpenidStudentRelative record, @Param("example") WechatOpenidStudentRelativeExample example);

    int updateByPrimaryKeySelective(WechatOpenidStudentRelative record);

    int updateByPrimaryKey(WechatOpenidStudentRelative record);
}