package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.Appreciate;
import com.hackerda.platform.infrastructure.database.model.AppreciateExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AppreciateMapper {
    long countByExample(AppreciateExample example);

    int deleteByExample(AppreciateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Appreciate record);

    int insertSelective(Appreciate record);

    List<Appreciate> selectByExample(AppreciateExample example);

    Appreciate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Appreciate record, @Param("example") AppreciateExample example);

    int updateByExample(@Param("record") Appreciate record, @Param("example") AppreciateExample example);

    int updateByPrimaryKeySelective(Appreciate record);

    int updateByPrimaryKey(Appreciate record);
}