package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.pojo.CourseTimetable;
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

    CourseTimetable toDO(CourseTimetableBO courseTimetable){
        CourseTimetable bo = new CourseTimetable();

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
}
