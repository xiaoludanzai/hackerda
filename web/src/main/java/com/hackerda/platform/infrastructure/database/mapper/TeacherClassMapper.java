package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.TeacherClass;
import com.hackerda.platform.infrastructure.database.model.example.TeacherClassExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeacherClassMapper {
    int countByExample(TeacherClassExample example);

    int deleteByExample(TeacherClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TeacherClass record);

    int insertSelective(TeacherClass record);

    List<TeacherClass> selectByExample(TeacherClassExample example);

    TeacherClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TeacherClass record, @Param("example") TeacherClassExample example);

    int updateByExample(@Param("record") TeacherClass record, @Param("example") TeacherClassExample example);

    int updateByPrimaryKeySelective(TeacherClass record);

    int updateByPrimaryKey(TeacherClass record);
}