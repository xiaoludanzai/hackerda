package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.TeacherCourseTimetableMapper;
import com.hackerda.platform.infrastructure.database.model.example.TeacherCourseTimetable;
import com.hackerda.platform.infrastructure.database.model.example.TeacherCourseTimetableExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherCourseTimeTableDao {
    @Resource
    private TeacherCourseTimetableMapper teacherCourseTimetableMapper;

    public List<TeacherCourseTimetable> selectByPojo(TeacherCourseTimetable teacherCourseTimetable){
        TeacherCourseTimetableExample example = new TeacherCourseTimetableExample();

        TeacherCourseTimetableExample.Criteria criteria = example.createCriteria();

        if(teacherCourseTimetable.getCourseTimetableId() != null){
            criteria.andCourseTimetableIdEqualTo(teacherCourseTimetable.getCourseTimetableId());
        }
        if(teacherCourseTimetable.getTermOrder() != null){
            criteria.andTermOrderEqualTo(teacherCourseTimetable.getTermOrder());
        }
        if(teacherCourseTimetable.getTermYear() != null){
            criteria.andTermYearEqualTo(teacherCourseTimetable.getTermYear());
        }
        if(teacherCourseTimetable.getTeacherId() != null){
            criteria.andTeacherIdEqualTo(teacherCourseTimetable.getTeacherId());
        }
        return teacherCourseTimetableMapper.selectByExample(example);
    }
}
