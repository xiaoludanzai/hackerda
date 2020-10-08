package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import com.hackerda.platform.utils.DESUtil;

import lombok.Data;

/**
 * student
 * @author 
 */
@Data
public class StudentUser implements Serializable {
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

    private Boolean hasCheck;

    private int count;


    private static final long serialVersionUID = 1L;
}