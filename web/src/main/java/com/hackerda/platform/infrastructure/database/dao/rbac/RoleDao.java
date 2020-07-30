package com.hackerda.platform.infrastructure.database.dao.rbac;

import com.hackerda.platform.infrastructure.database.mapper.ext.RoleExtMapper;
import com.hackerda.platform.infrastructure.database.model.Permission;
import com.hackerda.platform.infrastructure.database.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Repository
public class RoleDao {

    @Autowired
    private RoleExtMapper roleExtMapper;

    public void insertSelective(Role role){
        roleExtMapper.insertSelective(role);
    }

    public List<Permission> selectPermissionById(Integer id){
        return roleExtMapper.selectPermissionById(id);
    }
}
