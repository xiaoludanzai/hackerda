package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.CourseMapper;
import com.hackerda.platform.infrastructure.database.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseExtMapper extends CourseMapper {
    List<Course> selectByCourseList(List<Course> courseList);

    void insertBatch(List<Course> courseList);
}
