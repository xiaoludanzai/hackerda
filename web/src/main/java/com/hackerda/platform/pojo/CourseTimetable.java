package com.hackerda.platform.pojo;

import com.hackerda.platform.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CourseTimetable that = (CourseTimetable) o;

        return new EqualsBuilder()
                .append(courseId, that.courseId)
                .append(classDay, that.classDay)
                .append(classOrder, that.classOrder)
                .append(startWeek, that.startWeek)
                .append(endWeek, that.endWeek)
                .append(courseSequenceNumber, that.courseSequenceNumber)
                .append(termYear, that.termYear)
                .append(termOrder, that.termOrder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(courseId)
                .append(classDay)
                .append(classOrder)
                .append(startWeek)
                .append(endWeek)
                .append(courseSequenceNumber)
                .append(termYear)
                .append(termOrder)
                .toHashCode();
    }
}