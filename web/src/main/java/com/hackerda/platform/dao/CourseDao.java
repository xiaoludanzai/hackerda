package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.ext.CourseExtMapper;
import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.example.CourseExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CourseDao {
    @Resource
    private CourseExtMapper courseExtMapper;

    public List<Course> getAllCourse(){
        CourseExample example = new CourseExample();
        return courseExtMapper.selectByExample(example);
    }

    public List<Course> selectCourseByPojo(Course course){
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        if(course.getCourseOrder() != null){
            criteria.andCourseOrderEqualTo(course.getCourseOrder());
        }
        if(course.getNum() != null){
            criteria.andNumEqualTo(course.getNum());
        }
        if(course.getTermYear() != null){
            criteria.andTermYearEqualTo(course.getTermYear());
        }
        if(course.getTermOrder() != null){
            criteria.andTermOrderEqualTo(course.getTermOrder());
        }

        return courseExtMapper.selectByExample(example);
    }

    public Course selectByNumAndOrder(String number, String order){

        return selectCourseByPojo(new Course().setNum(number).setCourseOrder(order)).stream().findFirst().orElse(null);
    }

    public List<Course> selectByCourseList(List<Course> courseList){
        if(CollectionUtils.isEmpty(courseList)){
            return Collections.emptyList();
        }
        return courseExtMapper.selectByCourseList(courseList);
    }

    public void insertSelective(Course course){
        try {
            courseExtMapper.insertSelective(course);
        }catch (Exception e){
            log.error("error data {}", course, e);
        }

    }
}
