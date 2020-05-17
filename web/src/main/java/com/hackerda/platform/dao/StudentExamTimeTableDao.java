package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.StudentExamTimetableMapper;
import com.hackerda.platform.pojo.StudentExamTimetable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentExamTimeTableDao {
    @Resource
    private StudentExamTimetableMapper studentExamTimetableMapper;


    public void insertSelective(StudentExamTimetable record){
        studentExamTimetableMapper.insertSelective(record);
    }


}
