package com.hackerda.platform.pojo.vo;

import com.hackerda.platform.pojo.Student;
import com.hackerda.platform.pojo.constant.Academy;
import lombok.Data;

/**
 * @author JR Chan
 * @date 2019/6/10
 */
@Data
public class StudentInfoVo {

    private Integer account;

    private String password;

    private String name;

    private String sex;

    private String ethnic;

    private String className;

    private Academy academy;

    private Integer subject;


    StudentInfoVo(Student student) {
        this.account = student.getAccount();
        this.password = student.getPassword();
        this.name = student.getName();
        this.sex = student.getSex();
        this.ethnic = student.getEthnic();
    }
}
