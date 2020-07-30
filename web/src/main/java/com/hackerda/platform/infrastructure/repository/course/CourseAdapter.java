package com.hackerda.platform.infrastructure.repository.course;

import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.infrastructure.database.model.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseAdapter {

    CourseBO toBO(Course course){
        CourseBO bo = new CourseBO();

        return bo;

    }

    public Course toDO(CourseBO bo){
        Course course = new Course();

        course.setTermOrder(bo.getTermOrder());
        course.setTermYear(bo.getTermYear());
        course.setNum(bo.getNum());
        course.setCourseOrder(bo.getCourseOrder());
        course.setCredit(bo.getCredit());
        course.setExamType(bo.getExamType());
        course.setExamTypeCode(bo.getExamTypeCode());
        course.setName(bo.getName());

        course.setCourseType(bo.getCourseType());
        course.setCourseTypeCode(bo.getCourseTypeCode());

        course.setAcademyCode(bo.getAcademyCode());
        course.setAcademyName(bo.getAcademyName());

        course.setTeacherName(bo.getTeacherName());
        course.setTeacherAccount(bo.getTeacherAccount());

        return course;
    }
}
