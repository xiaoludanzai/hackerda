package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.ext.StudentUserExtMapper;
import com.hackerda.platform.infrastructure.database.model.Role;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.example.StudentUserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentUserDao {
    @Resource
    private StudentUserExtMapper studentUserExtMapper;

    public StudentUser selectStudentByAccount(int account){
        StudentUserExample studentUserExample = new StudentUserExample();
        studentUserExample.createCriteria().andAccountEqualTo(account);
        return studentUserExtMapper.selectByExample(studentUserExample).stream().findFirst().orElse(null);
    }

    public List<StudentUser> selectAllStudent(){
        StudentUserExample studentUserExample = new StudentUserExample();
        return studentUserExtMapper.selectByExample(studentUserExample);
    }

    public void insertStudentSelective(StudentUser studentUser){
        studentUserExtMapper.insertSelective(studentUser);
    }


    public void updatePassword(String account, String password){
        StudentUser student = new StudentUser();
        student.setAccount(Integer.parseInt(account));
        student.setPassword(password);
        student.setIsCorrect(true);

        StudentUserExample studentExample = new StudentUserExample();
        studentExample.createCriteria()
                .andAccountEqualTo(Integer.parseInt(account));
        studentUserExtMapper.updateByExampleSelective(student, studentExample);
    }

    public void updatePasswordUnCorrect(Integer account){
        StudentUser student = new StudentUser();
        student.setAccount(account);
        student.setIsCorrect(false);

        StudentUserExample studentExample = new StudentUserExample();
        studentExample.createCriteria()
                .andAccountEqualTo(account);
        studentUserExtMapper.updateByExampleSelective(student, studentExample);
    }

    public List<Role> selectRoleByAccount(String account){
        return studentUserExtMapper.selectRoleByAccount(Integer.parseInt(account));
    }


    public void saveOrUpdate(StudentUser studentUser){
        if(isExist(studentUser.getAccount())) {
            StudentUserExample example = new StudentUserExample();
            example.createCriteria().andAccountEqualTo(studentUser.getAccount());
            studentUserExtMapper.updateByExampleSelective(studentUser, example);
        } else {
            studentUserExtMapper.insertSelective(studentUser);
        }


    }

    private boolean isExist(int account) {
        StudentUserExample example = new StudentUserExample();
        example.createCriteria().andAccountEqualTo(account);
        return studentUserExtMapper.countByExample(example) == 1L ;
    }

}
