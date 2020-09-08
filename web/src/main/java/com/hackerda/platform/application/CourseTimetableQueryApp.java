package com.hackerda.platform.application;

import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableRepository;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseTimetableQueryApp {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseTimetableRepository courseTimetableRepository;

    public CourseTimeTableOverview getByAccount(int account, String termYear, int termOrder){

        StudentUserBO studentUserBO = studentRepository.getByAccount(new StudentAccount(account));

        CourseTimeTableOverview timeTableOverview;
        timeTableOverview = courseTimetableRepository.getByAccount(studentUserBO, termYear, termOrder);

        if(timeTableOverview.isEmpty() || !timeTableOverview.isCurrentTerm()){
            timeTableOverview = courseTimetableRepository.getByClassId(studentUserBO.getUrpClassNum().toString(), termYear,
                    termOrder);
        }

        if(timeTableOverview.isCurrentTerm()) {
            if(timeTableOverview.isPersonal()){
                courseTimetableRepository.saveByStudent(timeTableOverview.getNewList(), studentUserBO);
            }else {
                courseTimetableRepository.saveByClass(timeTableOverview.getNewList(), studentUserBO.getUrpClassNum().toString());
            }
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
