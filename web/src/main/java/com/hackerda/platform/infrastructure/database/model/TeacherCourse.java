package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeacherCourse {
    private Integer id;

    private String teacherId;

    private String courseId;

    private String courseOrder;

    private String termYear;

    private Integer termOrder;

}