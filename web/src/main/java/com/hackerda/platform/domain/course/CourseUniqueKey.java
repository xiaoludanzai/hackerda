package com.hackerda.platform.domain.course;


public class CourseUniqueKey {

    private final String courseId;
    private final String sequenceNumber;
    private final String termYear;
    private final int termOrder;


    CourseUniqueKey(String courseId, String sequenceNumber, String termYear, int termOrder){
        this.courseId = courseId;
        this.sequenceNumber = sequenceNumber;
        this.termYear = termYear;
        this.termOrder = termOrder;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public String getTermYear() {
        return termYear;
    }

    public int getTermOrder() {
        return termOrder;
    }
}
