package com.hackerda.platform.application;

import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseTimetableQueryApp {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private CourseTimetableRepository courseTimetableRepository;

    public CourseTimeTableOverview getByAccount(int account, String termYear, int termOrder){

        WechatStudentUserBO wechatStudentUserBO = studentUserRepository.getWetChatUserByAccount(account);

        CourseTimeTableOverview timeTableOverview;
        timeTableOverview = courseTimetableRepository.getByAccount(wechatStudentUserBO, termYear, termOrder);

        if(timeTableOverview.isEmpty()){
            timeTableOverview = courseTimetableRepository.getByClassId(wechatStudentUserBO.getUrpClassNum().toString(), termYear,
                    termOrder);
        }

        if(timeTableOverview.isPersonal()){
            courseTimetableRepository.saveByStudent(timeTableOverview.getNewList(), wechatStudentUserBO);
        }else {
            courseTimetableRepository.saveByClass(timeTableOverview.getNewList(), wechatStudentUserBO.getUrpClassNum().toString());
        }


        return timeTableOverview;
    }

    public CourseTimeTableOverview getByClassId(String classId, String termYear, int termOrder) {

        CourseTimeTableOverview timeTableOverview = courseTimetableRepository.getByClassId(classId,
                termYear, termOrder);

        courseTimetableRepository.saveByClass(timeTableOverview.getNewList(), classId);

        return timeTableOverview;
    }
}
