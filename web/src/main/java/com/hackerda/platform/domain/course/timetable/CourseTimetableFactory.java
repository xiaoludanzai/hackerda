package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.student.WechatStudentUserBO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseTimetableFactory {

    @Autowired
    private CourseTimetableRepository courseTimeTableRepository;

    public CourseTimeTableOverview createCurrent(WechatStudentUserBO wechatStudentUserBO, String termYear, int termOrder){
        return courseTimeTableRepository.getByAccount(wechatStudentUserBO, termYear, termOrder);
    }

}
