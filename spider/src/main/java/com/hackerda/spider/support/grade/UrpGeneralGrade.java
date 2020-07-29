package com.hackerda.spider.support.grade;

import com.alibaba.fastjson.annotation.JSONField;
import com.hackerda.spider.support.CourseRelativeInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Yuki
 * @date 2019/7/31 17:33
 */
@Data
public class UrpGeneralGrade {
    /**
     * 平均成绩
     */
    @JSONField(name = "avgcj")
    private Double avgGrade;
    /**
     * 班级号
     */
    private String bjh;
    /**
     * 班级名
     */
    private String bjm;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课序号
     */
    @JSONField(name = "coureSequenceNumber")
    private String courseSequenceNumber;
    /**
     * 课程属性编号
     */
    private String coursePropertyCode;
    /**
     * 课程属性名称
     */
    private String coursePropertyName;
    /**
     * 课程成绩
     */
    private Double courseScore;
    /**
     * 学分
     */
    private Double credit;
    /**
     * 课程英文名
     */
    private String englishCourseName;
    /**
     * 考试类型编号
     */
    private String examTypeCode;
    /**
     * 考试类型名称
     */
    private String examTypeName;
    /**
     * 绩点
     */
    private Double gradePoint;
    /**
     * 最高成绩
     */
    @JSONField(name = "maxcj")
    private Double maxGrade;
    /**
     * 最低成绩
     */
    @JSONField(name = "mincj")
    private Double minGrade;
    /**
     * 排名
     */
    private int rank;
    /**
     * 未通过原因编号
     */
    @JSONField(name = "unpassedReasonCode")
    private String unPassedReasonCode;
    /**
     * 未通过原因
     */
    @JSONField(name = "unpassedReasonExplain")
    private String unPassedReasonExplain;
    /**
     * 学期年份,如："2018-2019"
     */
    private String termCode;
    /**
     * 学期名称，如："第二学期"
     */
    private String termName;
    /**
     * 等级名
     */
    private String levelName;
    /**
     * 等级对应的数值
     */
    @JSONField(name = "levlePoint")
    private String levelPoint;
    /**
     * 操作时间
     */
    @JSONField(name = "operatetime")
    private String operateTime;
    /**
     * 操作人
     */
    private String operator;
    /**
     *
     */
    @JSONField(name = "persentlevlePoint")
    private String presentLevelPoint;
    /**
     * 计划名
     */
    private String planName;
    /**
     * 计划号
     */
    private String planNumber;
    /**
     * 项目名
     */
    private String programName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 替换课程编号
     */
    private String replaceCourseNumber;
    /**
     * 重修课程标识
     */
    private String retakeCourseMark;
    /**
     * 重修课程模式编号
     */
    private String retakeCourseModeCode;
    /**
     * 重修课程模式解释
     */
    private String retakeCourseModeExplain;
    /**
     * 标准分数
     */
    private String standardPoint;
    /**
     * 学习模式编号
     */
    @JSONField(name = "studingModeCode")
    private String studyingModeCode;
    /**
     * 学时
     */
    private String studyHour;
    /**
     *
     */
    private Double tPoint;
    /**
     * 学院号
     */
    private int xsh;
    /**
     * 学院名
     */
    private String xsm;
    /**
     * 专业号
     */
    private String zyh;
    /**
     * 相关课程信息
     */
    private CourseRelativeInfo id;


    private int getTermOrder(){
        switch (this.termName){
            case "第一学期": return 1;
            case "第二学期": return 2;
            default: throw new IllegalArgumentException("illegal termName");
        }
    }

}



