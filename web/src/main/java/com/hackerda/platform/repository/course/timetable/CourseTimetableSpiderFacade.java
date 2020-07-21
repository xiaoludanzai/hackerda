package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.CourseTimetable;

import java.util.List;

public interface CourseTimetableSpiderFacade {

    List<CourseTimetable> getCurrentTermTableByAccount(StudentUserBO studentUserBO);

    List<CourseTimetable> getCurrentTermTableByClassID(StudentUserBO studentUserBO);
}
