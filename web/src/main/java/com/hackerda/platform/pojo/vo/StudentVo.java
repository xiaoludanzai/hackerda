package com.hackerda.platform.pojo.vo;

import com.hackerda.platform.pojo.StudentUser;
import lombok.Data;

/**
 * @author JR Chan
 */
@Data
public class StudentVo {
    private Integer id;

    private Integer account;

    private String password;

    private String name;

    private String sex;

    private String ethnic;

    private Integer urpclassNum;

    private String academyName;

    private String subjectName;

    private String className;

    public StudentVo(StudentUser studentUser, String key){
        this.id = studentUser.getId();
        this.account = studentUser.getAccount();
        this.password = studentUser.getEnablePassword(key);
        this.name = studentUser.getName();
        this.ethnic = studentUser.getEthnic();
        this.urpclassNum = studentUser.getUrpclassNum();
        this.academyName = studentUser.getAcademyName();
        this.subjectName = studentUser.getSubjectName();
        this.className = studentUser.getClassName();

    }


}
