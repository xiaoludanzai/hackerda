package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.student.StudentUserBO;

import java.util.List;

public interface CourseTimetableRepository {

    CourseTimeTableOverview getByAccount(StudentUserBO wechatStudentUserBO, String termYear, int termOrder);

    CourseTimeTableOverview getByClassId(String classId, String termYear, int termOrder);

    void saveByStudent(List<CourseTimetableBO> tableList, StudentUserBO wechatStudentUserBO);

    void saveByClass(List<CourseTimetableBO> tableList, String classId);
}
