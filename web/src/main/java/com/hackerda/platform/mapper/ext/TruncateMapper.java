package com.hackerda.platform.mapper.ext;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TruncateMapper {

    @Update("truncate table grade")
    void grade();

    @Update("truncate table course")
    void course();

    @Update("truncate table course_timetable")
    void courseTimetable();

    @Update("truncate table class_course_timetable")
    void classCourseTimetable();

    @Update("truncate table student_course_time_table")
    void studentCourseTimetable();


    @Update("truncate table student_user")
    void studentUser();

    @Update("truncate table wechat_openid")
    void wechatOpenId();
}
