package com.hackerda.platform.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class CourseTimetableOverviewVO {

    private List<CourseTimeTableVo> courseTimetableVOList;

    private String errorMsg;

    private int errorCode;
}
