package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseTimetableQueryService {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private CourseTimetableRepository courseTimetableRepository;

    public CourseTimeTableOverview getByAccount(int account, String termYear, int termOrder){

        StudentUserBO studentUserBO = studentUserRepository.getByAccount(account);

        CourseTimeTableOverview timeTableOverview;
        timeTableOverview = courseTimetableRepository.getByAccount(studentUserBO, termYear, termOrder);

        if(timeTableOverview.isEmpty()){
            timeTableOverview = courseTimetableRepository.getByClassId(studentUserBO.getUrpClassNum().toString(), termYear,
                    termOrder);
        }

        if(timeTableOverview.isPersonal()){
            courseTimetableRepository.saveByStudent(timeTableOverview.getNewList(), studentUserBO);
        }else {
            courseTimetableRepository.saveByClass(timeTableOverview.getNewList(), studentUserBO.getUrpClassNum().toString());
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
