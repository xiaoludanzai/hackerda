package com.hackerda.spider;

import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;

import java.util.List;

public interface UrpSpider {

    void login(String account, String password);

    UrpStudentInfo getStudentInfo();

    List<UrpGeneralGrade> getCurrentGeneralGrade();

    List<Scheme> getSchemeGrade();

    UrpCourseTimeTable getUrpCourseTimeTable();

    List<UrpExamTime> getExamTime();

    void checkPassword(String account, String password);
}
