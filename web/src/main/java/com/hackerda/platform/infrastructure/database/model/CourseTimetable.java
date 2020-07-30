package com.hackerda.platform.infrastructure.database.model;

import com.hackerda.platform.utils.DateUtils;
import com.hackerda.platform.utils.Term;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
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

    public String getCampusName() {
        if(StringUtils.isEmpty(campusName)){
            return StringUtils.EMPTY;
        }
        return campusName;
    }

    public Integer getStudentCount() {
        if(studentCount == null){
            return 0;
        }
        return studentCount;
    }

    public Integer getClassDistinct() {
        if(classDistinct == null){
            return 0;
        }
        return classDistinct;
    }

    public String getRoomNumber() {
        if(StringUtils.isEmpty(roomNumber)){
            return StringUtils.EMPTY;
        }
        return roomNumber;
    }

    public Date getGmtCreate() {
        if(gmtCreate == null){
            return new Date();
        }
        return gmtCreate;
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