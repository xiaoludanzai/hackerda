package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.student.WechatStudentUserBO;

import java.util.List;

public interface CourseTimetableRepository {

    CourseTimeTableOverview getByAccount(WechatStudentUserBO wechatStudentUserBO, String termYear, int termOrder);

    CourseTimeTableOverview getByClassId(String classId, String termYear, int termOrder);

    void saveByStudent(List<CourseTimetableBO> tableList, WechatStudentUserBO wechatStudentUserBO);

    void saveByClass(List<CourseTimetableBO> tableList, String classId);
}
