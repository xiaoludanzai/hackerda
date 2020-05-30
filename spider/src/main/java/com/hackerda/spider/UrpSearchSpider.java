package com.hackerda.spider;

import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;

import java.util.List;

public interface UrpSearchSpider {

    List<List<CourseTimetableSearchResult>> getUrpCourseTimeTableByClassCode(String termYear, int termOrder,
                                                                             String classCode);
}
