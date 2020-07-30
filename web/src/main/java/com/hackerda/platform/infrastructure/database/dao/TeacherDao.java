package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.TeacherMapper;
import com.hackerda.platform.infrastructure.database.model.Teacher;
import com.hackerda.platform.infrastructure.database.model.example.TeacherExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherDao {
    @Resource
    private TeacherMapper teacherMapper;


    public List<Teacher> getAllTeacher(){
        TeacherExample example = new TeacherExample();
        return teacherMapper.selectByExample(example);
    }


    public List<Teacher> selectByPojo(Teacher teacher){
        TeacherExample example = new TeacherExample();

        TeacherExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotEmpty(teacher.getAccount())){
            criteria.andAccountEqualTo(teacher.getAccount());
        }

        if(StringUtils.isNotEmpty(teacher.getName())){
            criteria.andNameEqualTo(teacher.getName());
        }

        return teacherMapper.selectByExample(example);
    }

    public Teacher selectByAccount(String account){
        return selectByPojo(new Teacher().setAccount(account)).stream().findFirst().get();
    }
}
