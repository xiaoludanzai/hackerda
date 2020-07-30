package com.hackerda.platform.infrastructure.database.model;

import com.hackerda.platform.utils.DESUtil;
import lombok.Data;

import java.util.Date;

@Data
public class StudentUser {
    private Integer id;

    private Integer account;

    private String password;

    private String name;

    private String sex;

    private String ethnic;

    private Integer urpclassNum;

    private Boolean isCorrect;

    private Date gmtCreate;

    private Date gmtModified;

    private String academyName;

    private String subjectName;

    private String className;

    private int count;


    public String getEnablePassword(String key) {
        return DESUtil.decrypt(this.password, key);
    }


    /**
     * 获取年级
     * @return 2017级返回2017
     */
    public String getGrade(){
        return account.toString().substring(0, 4);
    }
}