package com.hackerda.platform.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@Accessors(chain = true)
public class Course {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return new EqualsBuilder()
                .append(num, course.num)
                .append(courseOrder, course.courseOrder)
                .append(termYear, course.termYear)
                .append(termOrder, course.termOrder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(num)
                .append(courseOrder)
                .append(termYear)
                .append(termOrder)
                .toHashCode();
    }
}