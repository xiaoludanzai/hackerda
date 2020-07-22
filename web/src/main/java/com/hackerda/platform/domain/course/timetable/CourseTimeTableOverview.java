package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Data
public class CourseTimeTableOverview {

    private List<CourseTimetableBO> courseTimetableBOList;

    private boolean isCurrentTerm;

    private boolean isPersonal;

    private boolean fetchSuccess;

    private boolean finishFetch;

    private String errorMsg;

    private int errorCode;

    public List<CourseTimetableBO> getNewList(){

        if(finishFetch || !fetchSuccess){
            return Collections.emptyList();
        }
        return courseTimetableBOList;
    }

    public boolean isEmpty(){
        return CollectionUtils.isEmpty(courseTimetableBOList);
    }
}
