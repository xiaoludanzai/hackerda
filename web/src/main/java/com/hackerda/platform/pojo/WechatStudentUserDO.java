package com.hackerda.platform.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class WechatStudentUserDO {

    private Integer id;

    private Integer account;

    private String password;

    private String name;

    private String sex;

    private String ethnic;

    private Integer urpClassNum;

    private Boolean isCorrect;

    private String academyName;

    private String subjectName;

    private String className;

    private String appId;

    private String openId;

    private Boolean isBind;

    private Boolean isSubscribe;

    private Integer scene;

    private Date gmtCreate;

    private Date gmtModified;
}
