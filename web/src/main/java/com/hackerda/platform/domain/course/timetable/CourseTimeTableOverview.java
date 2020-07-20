package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import lombok.Data;

import java.util.List;

@Data
public class CourseTimeTableOverview {

    private List<CourseTimetableBO> courseTimetableBOList;

    boolean isCurrentTerm;

    boolean isPersonal;

    private boolean fetchSuccess;

    private boolean finishFetch;

    private String errorMsg;

    private int errorCode;
}
