package com.hackerda.platform.infrastructure.database.dao.rbac;

import com.hackerda.platform.infrastructure.database.mapper.ext.RoleExtMapper;
import com.hackerda.platform.infrastructure.database.model.Permission;
import com.hackerda.platform.infrastructure.database.model.Role;
import com.hackerda.platform.infrastructure.database.model.RoleDetailDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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

    public List<RoleDetailDO> selectRoleDetailByCode(String code) {
        return roleExtMapper.selectRoleDetailByCode(code);
    }

    public void insertUserRoleRelative (String userName, List<String> roleCodeList) {
        roleExtMapper.insertUserRoleRelative(userName, roleCodeList);
    }

    public List<RoleDetailDO> selectRoleDetailByUserName(String userName) {

        return roleExtMapper.selectRoleDetailByUserName(userName);
    }

}
