package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeacherClass {
    private Integer id;

    private String teacherId;

    private Integer classId;

    private String termYear;

    private Integer termOrder;
}