package com.hackerda.platform.mapper;

import com.hackerda.platform.pojo.TeacherCourseTimetable;
import com.hackerda.platform.pojo.example.TeacherCourseTimetableExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeacherCourseTimetableMapper {
    int countByExample(TeacherCourseTimetableExample example);

    int deleteByExample(TeacherCourseTimetableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TeacherCourseTimetable record);

    int insertSelective(TeacherCourseTimetable record);

    List<TeacherCourseTimetable> selectByExample(TeacherCourseTimetableExample example);

    TeacherCourseTimetable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TeacherCourseTimetable record, @Param("example") TeacherCourseTimetableExample example);

    int updateByExample(@Param("record") TeacherCourseTimetable record, @Param("example") TeacherCourseTimetableExample example);

    int updateByPrimaryKeySelective(TeacherCourseTimetable record);

    int updateByPrimaryKey(TeacherCourseTimetable record);
}