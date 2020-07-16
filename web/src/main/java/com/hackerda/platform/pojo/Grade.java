package com.hackerda.platform.pojo;

import com.hackerda.platform.pojo.vo.CourseVO;
import com.hackerda.platform.pojo.vo.GradeVo;
import com.hackerda.platform.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class Grade {
    @EqualsAndHashCode.Exclude
    private Integer id;

    @EqualsAndHashCode.Exclude
    private Integer examId = 0;

    private Integer account;

    private Double score;

    private Double credit;

    @EqualsAndHashCode.Exclude
    private Double gradePoint;

    private String levelName;

    private String levelPoint;

    @EqualsAndHashCode.Exclude
    private Integer rank;

    private String courseName;

    private String courseNumber;

    private String courseOrder;

    private String coursePropertyCode;

    private String coursePropertyName;

    private String examTypeCode;

    private String examTypeName;

    private Integer studyHour;

    private String operateTime;

    private String operator;

    private String examTime;

    private String unpassedReasonCode;

    private String unpassedReasonExplain;

    private String remark;

    private String replaceCourseNumber;

    private String retakeCourseMark;

    private String retakecourseModeCode;

    private String retakeCourseModeExplain;

    private String standardPoint;

    private String termYear;

    private Integer termOrder;

    @EqualsAndHashCode.Exclude
    private Date gmtCreate;
    @EqualsAndHashCode.Exclude
    private Date gmtModify;

    private boolean update = false;

    public String getLevelName(){
        if(StringUtils.isEmpty(this.levelName)){
            return "";
        }
        return this.levelName;

    }

    public String getExamTypeCode() {
        if(StringUtils.isEmpty(this.examTypeCode)){
            return "";
        }
        return examTypeCode;
    }

    public String getUnpassedReasonCode() {
        if(StringUtils.isEmpty(this.unpassedReasonCode)){
            return "";
        }
        return unpassedReasonCode;
    }

    public String getUnpassedReasonExplain() {
        if(StringUtils.isEmpty(this.unpassedReasonExplain)){
            return "";
        }
        return unpassedReasonExplain;
    }

    public String getRemark() {
        if(StringUtils.isEmpty(this.remark)){
            return "";
        }
        return remark;
    }

    public String getReplaceCourseNumber() {
        if(StringUtils.isEmpty(this.replaceCourseNumber)){
            return "";
        }
        return replaceCourseNumber;
    }

    public String getRetakeCourseMark() {
        if(StringUtils.isEmpty(this.retakeCourseMark)){
            return "";
        }
        return retakeCourseMark;
    }

    public String getRetakecourseModeCode() {
        if(StringUtils.isEmpty(this.retakecourseModeCode)){
            return "";
        }
        return retakecourseModeCode;
    }

    public String getRetakeCourseModeExplain() {
        if(StringUtils.isEmpty(this.retakeCourseModeExplain)){
            return "";
        }
        return retakeCourseModeExplain;
    }

    public String getStandardPoint() {
        if(StringUtils.isEmpty(this.standardPoint)){
            return "";
        }
        return standardPoint;
    }

    public Grade setGradePoint(Double gradePoint) {
        if (gradePoint == 0 && this.score != -1) {
            double v = this.getScore() - 60;
            if (v < 0) {
                this.gradePoint = 0.0;
            } else {
                this.gradePoint = 1 + v / 10;
            }

        }else {
            this.gradePoint = gradePoint;
        }
        return this;
    }

    public boolean isCurrentTermGrade() {
        Term term = DateUtils.getCurrentSchoolTime().getTerm();
        return this.getTermYear().equals(term.getTermYear()) && this.getTermOrder().equals(term.getOrder());

    }



    public GradeVo toVo(){
        CourseVO course = new CourseVO();
        course.setCredit(this.getCredit().toString());
        course.setExamType(this.getExamTypeName());
        course.setExamTypeCode(this.getExamTypeCode());
        course.setCourseOrder(this.getCourseOrder());
        course.setNum(this.getCourseNumber());
        course.setName(this.getCourseName());
        course.setTermYear(this.getTermYear());
        course.setTermOrder(this.getTermOrder());
        return new GradeVo()
                .setCourse(course)
                .setAccount(this.getAccount())
                .setScore(this.getScore())
                .setGradePoint(this.getGradePoint())
                .setLevelName(this.getLevelName())
                .setLevelPoint(this.getLevelPoint())
                .setRank(this.getRank())
                .setReplaceCourseNumber(this.getReplaceCourseNumber())
                .setRemark(this.getRemark())
                .setRetakeCourseMark(this.getRetakeCourseMark())
                .setRetakecourseModeCode(this.getRetakecourseModeCode())
                .setRetakeCourseModeExplain(this.getRetakeCourseModeExplain())
                .setUnpassedReasonCode(this.getUnpassedReasonCode())
                .setUnpassedReasonExplain(this.getUnpassedReasonExplain())
                .setStandardPoint(this.getStandardPoint())
                .setTermYear(this.getTermYear())
                .setTermOrder(this.getTermOrder())
                .setUpdate(this.isUpdate())
                .setCoursePropertyCode(Optional.ofNullable(this.getCoursePropertyCode()).orElse(""))
                .setCoursePropertyName(this.getCoursePropertyName())
                .setExamTime(DateUtils.localDateToDate(this.getExamTime(), DateUtils.YYYY_MM_DD_PATTERN))
                .setExamTimeStr(this.getExamTime())
                .setOperateTime(parseGradeOperateTime(this.getOperateTime()))
                .setOperator(this.getOperator());


    }

    private Date parseGradeOperateTime(String text) {
        if (text.length() == 12) {
            text = text + "00";
        }
        return DateUtils.localDateToDate(text, DateUtils.PATTERN_WITHOUT_SPILT);
    }





}