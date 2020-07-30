package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentCourseTimeTable {
    private Integer id;

    private Integer studentId;

    private Integer courseTimetableId;

    private String termYear;

    private Integer termOrder;
}