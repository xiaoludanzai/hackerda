package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UrpClassroom implements Serializable {
    private Integer id;

    private String number;

    private String name = "";

    private String campusName;

    private String campusNumber;

    private Integer seats = 0;

    private String teachingBuildingName;

    private String teachingBuildingNumber;

    private String department = "";

    private Integer floor = 0;

    private String type = "";

}