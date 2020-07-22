package com.hackerda.platform.pojo.vo;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import lombok.Data;

import java.util.List;

@Data
public class CourseTimetableOverviewVO {

    private List<CourseTimeTableVo> courseTimetableVOList;

    private String errorMsg;

    private int errorCode;
}
