package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;
import lombok.ToString;

/**
 * 学生发布者
 */
@Data
public class StudentPosterDO {

    /** 用户名称 **/
    private String userName;

    /** 用户名称 **/
    private String nickName;

    /** 用户名称 **/
    private String avatarUrl;

    /**
     * 用户实名
     */
    private String name;

    private String sex;

    private Integer urpClassNum;

    private String academyName;

    private String subjectName;

    private String className;
}
