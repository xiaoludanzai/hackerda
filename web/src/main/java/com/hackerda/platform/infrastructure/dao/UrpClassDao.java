package com.hackerda.platform.infrastructure.dao;

import com.hackerda.platform.mapper.ext.UrpClassExtMapper;
import com.hackerda.platform.pojo.UrpClass;
import com.hackerda.platform.pojo.example.UrpClassExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UrpClassDao {
    @Resource
    private UrpClassExtMapper urpClassExtMapper;

    public List<UrpClass> selectAllClass(){
        return urpClassExtMapper.selectByExample(new UrpClassExample());
    }

    public UrpClass selectByClassNumber(String classNumber){
        UrpClassExample example = new UrpClassExample();
        example.createCriteria()
                .andClassNumEqualTo(classNumber);

        return urpClassExtMapper.selectByExample(example).stream().findFirst().orElse(null);

    }


    public UrpClass selectByName(String name){
        UrpClassExample example = new UrpClassExample();
        example.createCriteria()
                .andClassNameEqualTo(name);

        return urpClassExtMapper.selectByExample(example).stream().findFirst().orElse(null);
    }

    public void insertSelective(UrpClass urpClass){
        urpClassExtMapper.insertSelective(urpClass);
    }

    public void insertBatch(List<UrpClass> list){
        urpClassExtMapper.insertBatch(list);
    }

}
