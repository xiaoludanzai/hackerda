package com.hackerda.platform.repository;

import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.pojo.Grade;
import org.springframework.stereotype.Service;

@Service
public class GradeAdapter {

    public Grade toDO(GradeBO gradeBO) {
        Grade grade = new Grade();

        grade.setId(gradeBO.getId());

        grade.setAccount(gradeBO.getAccount());
        grade.setCourseName(gradeBO.getCourseName());
        grade.setCourseNumber(gradeBO.getCourseNumber());
        grade.setCourseOrder(gradeBO.getCourseOrder());
        grade.setCoursePropertyCode(gradeBO.getCoursePropertyCode());
        grade.setCoursePropertyName(gradeBO.getCoursePropertyName());

        grade.setCredit(gradeBO.getCredit());

        grade.setExamId(gradeBO.getExamId());
        grade.setExamTime(gradeBO.getExamTime());
        grade.setExamTypeCode(gradeBO.getExamTypeCode());
        grade.setExamTypeName(gradeBO.getExamTypeName());

        grade.setScore(gradeBO.getScore());
        grade.setGradePoint(gradeBO.getGradePoint());
        grade.setGmtCreate(gradeBO.getGmtCreate());
        grade.setGmtModify(gradeBO.getGmtModify());

        grade.setLevelName(gradeBO.getLevelName());
        grade.setLevelPoint(gradeBO.getLevelPoint());

        grade.setOperateTime(gradeBO.getOperateTimeStr());
        grade.setOperator(gradeBO.getOperator());

        grade.setRank(gradeBO.getRank());
        grade.setRemark(gradeBO.getRemark());
        grade.setReplaceCourseNumber(gradeBO.getReplaceCourseNumber());
        grade.setRetakeCourseMark(gradeBO.getRetakeCourseMark());
        grade.setRetakecourseModeCode(gradeBO.getRetakecourseModeCode());
        grade.setRetakeCourseModeExplain(gradeBO.getRetakeCourseModeExplain());

        grade.setStandardPoint(gradeBO.getStandardPoint());
        grade.setStudyHour(gradeBO.getStudyHour());

        grade.setTermOrder(gradeBO.getTermOrder());
        grade.setTermYear(gradeBO.getTermYear());

        grade.setUnpassedReasonCode(gradeBO.getUnpassedReasonCode());
        grade.setUnpassedReasonExplain(gradeBO.getUnpassedReasonExplain());


        return grade;
    }


    public GradeBO toBO(Grade grade) {
        GradeBO bo = new GradeBO();

        bo.setId(grade.getId());

        bo.setAccount(grade.getAccount());
        bo.setCourseName(grade.getCourseName());
        bo.setCourseNumber(grade.getCourseNumber());
        bo.setCourseOrder(grade.getCourseOrder());
        bo.setCoursePropertyCode(grade.getCoursePropertyCode());
        bo.setCoursePropertyName(grade.getCoursePropertyName());

        bo.setCredit(grade.getCredit());

        bo.setExamId(grade.getExamId());
        bo.setExamTime(grade.getExamTime());
        bo.setExamTypeCode(grade.getExamTypeCode());
        bo.setExamTypeName(grade.getExamTypeName());

        bo.setGradePoint(grade.getGradePoint());
        bo.setGmtCreate(grade.getGmtCreate());
        bo.setGmtModify(grade.getGmtModify());

        bo.setLevelName(grade.getLevelName());
        bo.setLevelPoint(grade.getLevelPoint());

        bo.setOperateTime(grade.getOperateTime());
        bo.setOperator(grade.getOperator());

        bo.setRank(grade.getRank());
        bo.setRemark(grade.getRemark());
        bo.setReplaceCourseNumber(grade.getReplaceCourseNumber());
        bo.setRetakeCourseMark(grade.getRetakeCourseMark());
        bo.setRetakecourseModeCode(grade.getRetakecourseModeCode());
        bo.setRetakeCourseModeExplain(grade.getRetakeCourseModeExplain());

        bo.setScore(grade.getScore());
        bo.setStandardPoint(grade.getStandardPoint());
        bo.setStudyHour(grade.getStudyHour());

        bo.setTermOrder(grade.getTermOrder());
        bo.setTermYear(grade.getTermYear());

        bo.setUnpassedReasonCode(grade.getUnpassedReasonCode());
        bo.setUnpassedReasonExplain(grade.getUnpassedReasonExplain());


        CourseBO course = new CourseBO();
        course.setCredit(grade.getCredit().toString());
        course.setExamType(grade.getExamTypeName());
        course.setExamTypeCode(grade.getExamTypeCode());
        course.setCourseOrder(grade.getCourseOrder());
        course.setNum(grade.getCourseNumber());
        course.setName(grade.getCourseName());
        course.setTermYear(grade.getTermYear());
        course.setTermOrder(grade.getTermOrder());

        bo.setCourse(course);

        return bo;
    }
}
