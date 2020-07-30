package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.StudentExamTimetableMapper;
import com.hackerda.platform.infrastructure.database.model.StudentExamTimetable;
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
