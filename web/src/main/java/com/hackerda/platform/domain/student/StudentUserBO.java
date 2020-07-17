package com.hackerda.platform.domain.student;

import com.hackerda.platform.pojo.WechatOpenid;
import lombok.Data;

import java.util.List;

@Data
public class StudentUserBO {

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

    private List<WechatOpenid> wechatOpenidList;

}
