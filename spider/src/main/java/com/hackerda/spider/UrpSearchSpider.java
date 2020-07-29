package com.hackerda.spider;

import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import com.hackerda.spider.support.search.SearchResult;
import com.hackerda.spider.support.search.classInfo.ClassInfoSearchResult;
import com.hackerda.spider.support.search.classInfo.SearchClassInfoPost;
import com.hackerda.spider.support.search.classroom.SearchClassroomPost;
import com.hackerda.spider.support.search.classroom.SearchClassroomResult;
import com.hackerda.spider.support.search.classroom.SearchResultWrapper;
import com.hackerda.spider.support.search.course.SearchCoursePost;
import com.hackerda.spider.support.search.course.SearchCourseResult;
import com.hackerda.spider.support.search.emptyroom.SearchEmptyRoomPost;
import com.hackerda.spider.support.search.emptyroom.EmptyRoomRecord;
import com.hackerda.spider.support.search.teacher.SearchTeacherPost;
import com.hackerda.spider.support.search.teacher.SearchTeacherResult;

import java.util.List;

public interface UrpSearchSpider {


    List<List<CourseTimetableSearchResult>> searchClassTimeTable(String termYear, int termOrder, String classCode);

    List<List<CourseTimetableSearchResult>> searchCourseTimetableByTeacher(String termYear, int termOrder,
                                                                           String teacherNumber);

    List<List<CourseTimetableSearchResult>> searchCourseTimeTable(String termYear, int termOrder, String courseId,
                                                                  String courseOrder);

    List<SearchResult<ClassInfoSearchResult>> searchClassInfo(SearchClassInfoPost searchClassInfoPost);

    List<SearchResult<SearchTeacherResult>> searchTeacherInfo(SearchTeacherPost searchTeacherPost);

    List<SearchResultWrapper<SearchClassroomResult>> searchClassroomInfo(SearchClassroomPost searchClassroomPost);

    SearchResult<SearchCourseResult> searchCourseInfo(SearchCoursePost searchCoursePost);

    List<SearchResultWrapper<EmptyRoomRecord>>  searchEmptyRoom(SearchEmptyRoomPost searchEmptyRoomPost);
}
