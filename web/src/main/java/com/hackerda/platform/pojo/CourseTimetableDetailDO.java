package com.hackerda.platform.pojo;



import lombok.Data;

import java.util.Date;

@Data
public class CourseTimetableDetailDO {
    private Integer id;

    private String roomName;

    private String roomNumber;

    private String campusName;

    private Integer continuingSession;

    private String courseId;

    private String attendClassTeacher;

    private Integer studentCount;

    private Integer classDay;

    private Integer classOrder;

    private Integer startWeek;

    private Integer endWeek;

    private String classInSchoolWeek;

    private String courseSequenceNumber;

    private String weekDescription;

    private Integer classDistinct;

    private String termYear;

    private Integer termOrder;

    private Date gmtCreate;

    private String courseName;

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
