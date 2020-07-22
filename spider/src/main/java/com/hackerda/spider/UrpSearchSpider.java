package com.hackerda.spider;

import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;

import java.util.List;

public interface UrpSearchSpider {


    List<List<CourseTimetableSearchResult>> searchClassTimeTable(String termYear, int termOrder, String classCode);

    List<List<CourseTimetableSearchResult>> searchCourseTimetableByTeacher(String termYear, int termOrder,
                                                                           String teacherNumber);

    List<List<CourseTimetableSearchResult>> searchCourseTimeTable(String termYear, int termOrder, String courseId,
                                                                  String courseOrder);
}
