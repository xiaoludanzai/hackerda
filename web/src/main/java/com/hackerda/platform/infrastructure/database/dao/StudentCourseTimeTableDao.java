package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.StudentCourseTimeTableMapper;
import com.hackerda.platform.infrastructure.database.model.StudentCourseTimeTable;
import com.hackerda.platform.infrastructure.database.model.example.StudentCourseTimeTableExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentCourseTimeTableDao {
    @Resource
    private StudentCourseTimeTableMapper studentCourseTimeTableMapper;

    public void insertSelective(StudentCourseTimeTable studentCourseTimeTable){
        studentCourseTimeTableMapper.insertSelective(studentCourseTimeTable);
    }

    public List<StudentCourseTimeTable> selectByExample(StudentCourseTimeTable studentCourseTimeTable){
        StudentCourseTimeTableExample example = new StudentCourseTimeTableExample();
        StudentCourseTimeTableExample.Criteria criteria = example.createCriteria();

        if(studentCourseTimeTable.getTermOrder() != null){
            criteria.andTermOrderEqualTo(studentCourseTimeTable.getTermOrder());
        }
        if(studentCourseTimeTable.getTermYear() != null){
            criteria.andTermYearEqualTo(studentCourseTimeTable.getTermYear());
        }
        if(studentCourseTimeTable.getStudentId() != null){
            criteria.andStudentIdEqualTo(studentCourseTimeTable.getStudentId());
        }
        if(studentCourseTimeTable.getCourseTimetableId() != null){
            criteria.andCourseTimetableIdEqualTo(studentCourseTimeTable.getCourseTimetableId());
        }

        return studentCourseTimeTableMapper.selectByExample(example);
    }

    public int deleteByAccount(Integer account){
        StudentCourseTimeTableExample example = new StudentCourseTimeTableExample();
        StudentCourseTimeTableExample.Criteria criteria = example.createCriteria();

        criteria.andStudentIdEqualTo(account);
        return studentCourseTimeTableMapper.deleteByExample(example);
    }
}
