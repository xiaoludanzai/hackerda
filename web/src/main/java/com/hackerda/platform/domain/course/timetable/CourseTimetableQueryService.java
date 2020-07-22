package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.repository.student.StudentUserRepository;
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
            timeTableOverview = courseTimetableRepository.getByAccount(studentUserBO, termYear, termOrder);
        }

        if(timeTableOverview.isPersonal()){
            courseTimetableRepository.saveByStudent(timeTableOverview.getNewList(), studentUserBO);
        }else {
            courseTimetableRepository.saveByClass(timeTableOverview.getNewList(), studentUserBO);
        }


        return timeTableOverview;
    }
}
