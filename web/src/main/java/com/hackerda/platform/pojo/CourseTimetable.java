package com.hackerda.platform.pojo;

import com.hackerda.platform.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CourseTimetable {
    @EqualsAndHashCode.Exclude
    private Integer id;

    private String roomName;

    private String roomNumber;

    private String campusName;

    private Integer continuingSession;

    private String courseId;

    @EqualsAndHashCode.Exclude
    private String attendClassTeacher;

    @EqualsAndHashCode.Exclude
    private Integer studentCount;

    private Integer classDay;

    private Integer classOrder;

    private Integer startWeek;

    private Integer endWeek;

    private String classInSchoolWeek;

    private String courseSequenceNumber;

    private String weekDescription;

    @EqualsAndHashCode.Exclude
    private Integer classDistinct;

    private String termYear;

    private Integer termOrder;

    @EqualsAndHashCode.Exclude
    private Date gmtCreate;


    public boolean isCurrentTerm(){
        Term term = DateUtils.getCurrentSchoolTime().getTerm();
        return getTermOrder().equals(term.getOrder()) &&
                getTermYear().equals(term.getTermYear());
    }

    public ClassCourseTimetable getClassRelative(String classNum){
        return new ClassCourseTimetable()
                .setClassId(classNum)
                .setCourseTimetableId(getId())
                .setTermOrder(getTermOrder())
                .setTermYear(getTermYear());
    }

}