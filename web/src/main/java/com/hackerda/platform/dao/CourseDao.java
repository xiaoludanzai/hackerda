package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.CourseMapper;
import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.example.CourseExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CourseDao {
    @Resource
    private CourseMapper courseMapper;

    public List<Course> getAllCourse(){
        CourseExample example = new CourseExample();
        return courseMapper.selectByExample(example);
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

        return courseMapper.selectByExample(example);
    }

    public Course selectByNumAndOrder(String number, String order){

        return selectCourseByPojo(new Course().setNum(number).setCourseOrder(order)).stream().findFirst().orElse(null);
    }

    public void insertSelective(Course course){
        try {
            courseMapper.insertSelective(course);
        }catch (Exception e){
            log.error("error data {}", course, e);
        }

    }
}
