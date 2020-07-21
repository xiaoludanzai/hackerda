package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.student.StudentUserBO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseTimetableFactory {

    @Autowired
    private CourseTimetableRepository courseTimeTableRepository;

    public CourseTimeTableOverview createCurrent(StudentUserBO studentUserBO){
        return courseTimeTableRepository.getCurrentTermTable(studentUserBO);
    }

}
