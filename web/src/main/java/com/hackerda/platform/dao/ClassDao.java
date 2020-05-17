package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.ClassesMapper;
import com.hackerda.platform.pojo.Classes;
import com.hackerda.platform.pojo.example.ClassesExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author junrong.chen
 * @date 2019/7/19
 */
@Service
public class ClassDao {
    @Resource
    private ClassesMapper classesMapper;




    public void insertClass(Classes classes){
        classesMapper.insert(classes);
    }

    public List<Classes> getAllClass() {
        ClassesExample classesExample = new ClassesExample();
        classesExample.createCriteria();
        return classesMapper.selectByExample(classesExample);
    }

}
