package com.hackerda.platform.service.grade;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.pojo.Grade;
import com.hackerda.platform.pojo.GradeDetail;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.repository.GradeSpiderFacade;
import com.hackerda.platform.service.NewUrpSpiderService;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.scheme.Scheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeSpiderFacadeImpl implements GradeSpiderFacade {
    @Autowired
    private NewUrpSpiderService newUrpSpiderService;

    @Override
    public List<Grade> getCurrentTermGrade(StudentUser student) {

        List<UrpGeneralGrade> generalGrade = newUrpSpiderService.getCurrentGeneralGrade(student);

        List<GradeDetail> gradeDetailList = generalGrade.stream().map(urpGeneralGrade -> {

            Grade grade = new Grade()
                    .setAccount(Integer.parseInt(urpGeneralGrade.getId().getStudentNumber()))
                    .setCourseNumber(urpGeneralGrade.getId().getCourseNumber())
                    .setCourseOrder(urpGeneralGrade.getCourseSequenceNumber())
                    .setScore(urpGeneralGrade.getCourseScore() == null ? -1 : urpGeneralGrade.getCourseScore())
                    .setGradePoint(urpGeneralGrade.getGradePoint())
                    .setLevelName(urpGeneralGrade.getLevelName())
                    .setLevelPoint(urpGeneralGrade.getLevelPoint())
                    .setRank(urpGeneralGrade.getRank())
                    .setReplaceCourseNumber(urpGeneralGrade.getReplaceCourseNumber())
                    .setRemark(urpGeneralGrade.getRemark())
                    .setRetakeCourseMark(urpGeneralGrade.getRetakeCourseMark())
                    .setRetakecourseModeCode(urpGeneralGrade.getRetakeCourseModeCode())
                    .setRetakeCourseModeExplain(urpGeneralGrade.getRetakeCourseModeExplain())
                    .setUnpassedReasonCode(urpGeneralGrade.getUnPassedReasonCode())
                    .setUnpassedReasonExplain(urpGeneralGrade.getUnPassedReasonExplain())
                    .setStandardPoint(urpGeneralGrade.getStandardPoint())
                    .setTermYear(urpGeneralGrade.getId().getTermYear())
                    .setTermOrder(urpGeneralGrade.getId().getTermOrder())
                    .setExamTime(urpGeneralGrade.getId().getExamtime())
                    .setOperateTime(urpGeneralGrade.getOperateTime())
                    .setOperator(urpGeneralGrade.getOperator())
                    .setStudyHour(StringUtils.isEmpty(urpGeneralGrade.getStudyHour()) ? 0 :
                            Integer.parseInt(urpGeneralGrade.getStudyHour()))
                    .setCourseName(urpGeneralGrade.getCourseName())
                    .setCoursePropertyCode(urpGeneralGrade.getCoursePropertyCode())
                    .setCoursePropertyName(urpGeneralGrade.getCoursePropertyName())
                    .setExamTypeName(urpGeneralGrade.getExamTypeName())
                    .setExamTypeCode(urpGeneralGrade.getExamTypeCode())
                    .setCredit(urpGeneralGrade.getCredit());

            return new GradeDetail(grade, null);

        }).collect(Collectors.toList());


        return gradeDetailList.stream()
                .map(GradeDetail::getGrade)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grade> getSchemeGrade(StudentUser student) {
        return newUrpSpiderService.getSchemeGrade(student)
                .stream()
                .map(Scheme::getCjList)
                .flatMap(Collection::stream)
                .map(item -> new Grade()
                        .setCredit(Double.parseDouble(item.getCredit()))
                        .setExamTypeCode(item.getExamTypeCode())
                        .setCoursePropertyName(item.getCourseAttributeName())
                        .setCoursePropertyCode(item.getCourseAttributeCode())
                        .setCourseName(item.getCourseName())
                        .setCourseNumber(item.getId().getCourseNumber())
                        .setCourseOrder(item.getId().getCourseSequenceNumber())
                        .setOperator(item.getOperator())
                        .setOperateTime(item.getOperatingTime())
                        .setScore(Double.parseDouble(item.getScore()))
                        .setExamTime(item.getExamTime())
                        .setAccount(Integer.parseInt(item.getId().getStudentId()))
                        .setExamTypeCode(item.getExamTypeCode())
                        .setExamTypeName(item.getExamTypeName())
                        .setLevelName(item.getGradeName())
                        .setStandardPoint(item.getStandardScore())
                        .setLevelPoint(Integer.toString(item.getGradeScore()))
                        .setGradePoint(Double.parseDouble(Optional.ofNullable(item.getGradePointScore()).orElse("0")))
                        .setTermYear(item.getTermYear())
                        .setTermOrder(item.getTermCode())
                        .setStudyHour(item.getCycle() == null ? 0 : Integer.parseInt(item.getCycle()))
                        .setRank(0))
                .collect(Collectors.toList());
    }
}
