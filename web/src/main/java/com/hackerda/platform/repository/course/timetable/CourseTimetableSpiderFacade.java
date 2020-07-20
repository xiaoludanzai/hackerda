package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.domain.student.StudentUserBO;

import java.util.List;

public interface CourseTimetableSpiderFacade {

    List<CourseTimetableBO> getCurrentTermTable(StudentUserBO studentUserBO);
}
