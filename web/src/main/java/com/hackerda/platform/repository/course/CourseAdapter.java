package com.hackerda.platform.repository.course;

import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.pojo.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseAdapter {

    CourseBO toBO(Course course){
        CourseBO bo = new CourseBO();

        return bo;

    }
}
