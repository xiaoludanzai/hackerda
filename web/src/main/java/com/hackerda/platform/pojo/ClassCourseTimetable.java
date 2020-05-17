package com.hackerda.platform.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClassCourseTimetable {
    private Integer id;

    private String classId;

    private Integer courseTimetableId;

    private String termYear;

    private Integer termOrder;



}