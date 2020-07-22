package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.pojo.CourseTimetable;
import com.hackerda.platform.pojo.CourseTimetableDetailDO;
import org.springframework.stereotype.Service;

@Service
public class CourseTimetableAdapter {


    CourseTimetableBO toBO(CourseTimetable courseTimetable){
        CourseTimetableBO bo = new CourseTimetableBO();

        bo.setId(courseTimetable.getId());

        bo.setAttendClassTeacher(courseTimetable.getAttendClassTeacher());

        bo.setCampusName(courseTimetable.getCampusName());

        bo.setClassDay(courseTimetable.getClassDay());
        bo.setClassDistinct(courseTimetable.getClassDistinct());
        bo.setClassInSchoolWeek(courseTimetable.getClassInSchoolWeek());
        bo.setClassOrder(courseTimetable.getClassOrder());
        bo.setContinuingSession(courseTimetable.getContinuingSession());
        bo.setCourseId(courseTimetable.getCourseId());
        bo.setCourseSequenceNumber(courseTimetable.getCourseSequenceNumber());

        bo.setStartWeek(courseTimetable.getStartWeek());
        bo.setEndWeek(courseTimetable.getEndWeek());

        bo.setRoomName(courseTimetable.getRoomName());
        bo.setRoomNumber(courseTimetable.getRoomNumber());

        bo.setStudentCount(courseTimetable.getStudentCount());

        bo.setWeekDescription(courseTimetable.getWeekDescription());

        bo.setTermOrder(courseTimetable.getTermOrder());
        bo.setTermYear(courseTimetable.getTermYear());

        bo.setStudentCount(bo.getStudentCount());

        return bo;
    }

    CourseTimetableBO toBO(CourseTimetableDetailDO detail){
        CourseTimetableBO bo = new CourseTimetableBO();

        bo.setId(detail.getId());

        bo.setAttendClassTeacher(detail.getAttendClassTeacher());

        bo.setCampusName(detail.getCampusName());

        bo.setClassDay(detail.getClassDay());
        bo.setClassDistinct(detail.getClassDistinct());
        bo.setClassInSchoolWeek(detail.getClassInSchoolWeek());
        bo.setClassOrder(detail.getClassOrder());
        bo.setContinuingSession(detail.getContinuingSession());
        bo.setCourseId(detail.getCourseId());
        bo.setCourseSequenceNumber(detail.getCourseSequenceNumber());

        bo.setStartWeek(detail.getStartWeek());
        bo.setEndWeek(detail.getEndWeek());

        bo.setRoomName(detail.getRoomName());
        bo.setRoomNumber(detail.getRoomNumber());

        bo.setStudentCount(detail.getStudentCount());

        bo.setWeekDescription(detail.getWeekDescription());

        bo.setTermOrder(detail.getTermOrder());
        bo.setTermYear(detail.getTermYear());

        bo.setStudentCount(detail.getStudentCount());
        bo.setGmtCreate(detail.getGmtCreate());


        CourseBO courseBO = new CourseBO();

        courseBO.setName(detail.getCourseName());
        courseBO.setCredit(detail.getCredit());
        courseBO.setNum(detail.getCourseId());
        courseBO.setCourseOrder(detail.getCourseSequenceNumber());
        courseBO.setExamType(detail.getExamType());
        courseBO.setExamTypeCode(detail.getExamTypeCode());
        courseBO.setTermOrder(detail.getTermOrder());
        courseBO.setTermYear(detail.getTermYear());
        courseBO.setAcademyCode(detail.getAcademyCode());
        courseBO.setAcademyName(detail.getAcademyName());
        courseBO.setTeacherName(detail.getTeacherName());

        courseBO.setCourseTypeCode(detail.getCourseTypeCode());
        courseBO.setCourseType(detail.getCourseType());

        bo.setCourseBO(courseBO);

        return bo;
    }

    CourseTimetable toDO(CourseTimetableBO courseTimetable){
        CourseTimetable tableDO = new CourseTimetable();

        tableDO.setId(courseTimetable.getId());

        tableDO.setAttendClassTeacher(courseTimetable.getAttendClassTeacher());

        tableDO.setCampusName(courseTimetable.getCampusName());

        tableDO.setClassDay(courseTimetable.getClassDay());
        tableDO.setClassDistinct(courseTimetable.getClassDistinct());
        tableDO.setClassInSchoolWeek(courseTimetable.getClassInSchoolWeek());
        tableDO.setClassOrder(courseTimetable.getClassOrder());
        tableDO.setContinuingSession(courseTimetable.getContinuingSession());
        tableDO.setCourseId(courseTimetable.getCourseId());
        tableDO.setCourseSequenceNumber(courseTimetable.getCourseSequenceNumber());

        tableDO.setStartWeek(courseTimetable.getStartWeek());
        tableDO.setEndWeek(courseTimetable.getEndWeek());

        tableDO.setRoomName(courseTimetable.getRoomName());
        tableDO.setRoomNumber(courseTimetable.getRoomNumber());

        tableDO.setStudentCount(courseTimetable.getStudentCount());

        tableDO.setWeekDescription(courseTimetable.getWeekDescription());

        tableDO.setTermOrder(courseTimetable.getTermOrder());
        tableDO.setTermYear(courseTimetable.getTermYear());

        tableDO.setStudentCount(tableDO.getStudentCount());


        return tableDO;
    }
}
