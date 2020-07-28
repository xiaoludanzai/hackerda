package com.hackerda.platform.infrastructure.dao.rbac;

import com.hackerda.platform.mapper.PermissionMapper;
import com.hackerda.platform.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author JR Chan
 */
@Repository
public class PermissionDao {

    @Autowired
    private PermissionMapper permissionMapper;

    public void insertSelective(Permission permission){
        permissionMapper.insertSelective(permission);
    }
}
