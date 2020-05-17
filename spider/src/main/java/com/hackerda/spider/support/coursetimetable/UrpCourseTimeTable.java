package com.hackerda.spider.support.coursetimetable;


import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.hackerda.spider.support.CourseRelativeInfo;
import lombok.Data;

import java.util.List;

/**
 * @author Yuki
 * @date 2019/8/29 21:43
 */
@Data
public class UrpCourseTimeTable {
    /**
     * 任课教师
     */
    private String attendClassTeacher;
    /**
     * 课程分类编号
     */
    private String courseCategoryCode;
    /**
     * 课程分类名称
     */
    private String courseCategoryName;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程属性编号
     */
    private String coursePropertiesCode;
    /**
     * 课程属性名称
     */
    private String coursePropertiesName;
    /**
     *
     */
    private String dgFlag;
    /**
     * 考试分类编号
     */
    private String examTypeCode;
    /**
     * 考试分类名称
     */
    private String examTypeName;
    /**
     * 标识
     */
    private String flag;
    /**
     * 课程相关信息
     */
    @JSONField(name = "id")
    private CourseRelativeInfo courseRelativeInfo;
    /**
     * 项目计划名称
     */
    private String programPlanName;
    /**
     * 项目计划编号
     */
    private String programPlanNumber;
    /**
     * 限制条件
     */
    private String restrictedCondition;
    /**
     *
     */
    private String rlFlag;
    /**
     * 选择课程状态编号
     */
    private String selectCourseStatusCode;
    /**
     * 选择课程状态名称
     */
    private String selectCourseStatusName;
    /**
     * 学习模式编号
     */
    private String studyModeCode;
    /**
     * 学习模式名称
     */
    private String studyModeName;
    /**
     * 时间和上课地点
     */
    private List<TimeAndPlace> timeAndPlaceList;
    /**
     * 学分
     */
    private Double unit;
    /**
     *
     */
    private String ywdgFlag;


    private String[] parseTermYearAndTermOrder(String executiveEducationPlanNumber) {
        String[] results = executiveEducationPlanNumber.split("-");
        return new String[]{results[0] + "-" + results[1], results[2]};
    }



}
