package com.hackerda.platform.mapper;

import com.hackerda.platform.pojo.StudentCourseTimeTable;
import com.hackerda.platform.pojo.example.StudentCourseTimeTableExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentCourseTimeTableMapper {
    int countByExample(StudentCourseTimeTableExample example);

    int deleteByExample(StudentCourseTimeTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentCourseTimeTable record);

    int insertSelective(StudentCourseTimeTable record);

    List<StudentCourseTimeTable> selectByExample(StudentCourseTimeTableExample example);

    StudentCourseTimeTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentCourseTimeTable record, @Param("example") StudentCourseTimeTableExample example);

    int updateByExample(@Param("record") StudentCourseTimeTable record, @Param("example") StudentCourseTimeTableExample example);

    int updateByPrimaryKeySelective(StudentCourseTimeTable record);

    int updateByPrimaryKey(StudentCourseTimeTable record);
}