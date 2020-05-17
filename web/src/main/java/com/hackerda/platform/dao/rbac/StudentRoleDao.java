package com.hackerda.platform.dao.rbac;

import com.hackerda.platform.mapper.ext.StudentRoleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Repository
public class StudentRoleDao {

    @Autowired
    private StudentRoleExtMapper studentRoleExtMapper;

    public void insertBatch(Integer account, List<Integer> roleIdList){
        studentRoleExtMapper.insertBatch(account, roleIdList);
    }

}
