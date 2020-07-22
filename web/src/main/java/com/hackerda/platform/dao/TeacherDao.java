package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.TeacherMapper;
import com.hackerda.platform.pojo.Teacher;
import com.hackerda.platform.pojo.example.TeacherExample;
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
