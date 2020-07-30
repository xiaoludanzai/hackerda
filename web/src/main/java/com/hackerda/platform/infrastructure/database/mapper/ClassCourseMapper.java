package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.ClassCourse;
import com.hackerda.platform.infrastructure.database.model.example.ClassCourseExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassCourseMapper {
    int countByExample(ClassCourseExample example);

    int deleteByExample(ClassCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClassCourse record);

    int insertSelective(ClassCourse record);

    List<ClassCourse> selectByExample(ClassCourseExample example);

    ClassCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClassCourse record, @Param("example") ClassCourseExample example);

    int updateByExample(@Param("record") ClassCourse record, @Param("example") ClassCourseExample example);

    int updateByPrimaryKeySelective(ClassCourse record);

    int updateByPrimaryKey(ClassCourse record);
}