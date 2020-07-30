package com.hackerda.platform.controller.vo;

import com.hackerda.platform.infrastructure.database.model.Course;
import com.hackerda.platform.infrastructure.database.model.Teacher;
import com.hackerda.platform.infrastructure.database.model.UrpClass;
import com.hackerda.platform.infrastructure.database.model.UrpClassroom;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CourseTimetableSearchResultVo {

    private Course course;

    private List<Teacher> teacherList;

    private List<UrpClass> urpClassList;

    private UrpClassroom classroom;

    private int startWeek;

    private int endWeek;

    private String weekDescription;

    private String termYear;

    private int termOrder;

    private int classDay;

    private int classOrder;

    private String academyName;

    private List<String> subjectNameList;
}
