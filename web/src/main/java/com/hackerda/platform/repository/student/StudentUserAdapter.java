package com.hackerda.platform.repository.student;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.StudentUser;
import org.springframework.stereotype.Service;

@Service
public class StudentUserAdapter {

    public StudentUserBO toBO(StudentUser studentUser){
        StudentUserBO bo = new StudentUserBO();

        bo.setAcademyName(studentUser.getAcademyName());
        bo.setAccount(studentUser.getAccount());
        bo.setClassName(studentUser.getClassName());
        bo.setEthnic(studentUser.getEthnic());
        bo.setIsCorrect(studentUser.getIsCorrect());

        bo.setSex(studentUser.getSex());
        bo.setSubjectName(studentUser.getSubjectName());
        bo.setUrpClassNum(studentUser.getUrpclassNum());
        bo.setPassword(studentUser.getPassword());
        bo.setName(studentUser.getName());

        return bo;
    }
}
