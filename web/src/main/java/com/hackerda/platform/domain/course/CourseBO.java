package com.hackerda.platform.domain.course;

import lombok.Data;

@Data
public class CourseBO {

    private Integer id;

    private String name;

    private String num;

    private String courseOrder;

    private String termYear;

    private Integer termOrder;

    private String teacherAccount;

    private String teacherName;

    private String examType;

    private String examTypeCode;

    private String academyName;

    private String academyCode;

    private String courseType;

    private String courseTypeCode;

    private String credit;
}
