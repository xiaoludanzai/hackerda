package com.hackerda.platform.dao.rbac;

import com.hackerda.platform.mapper.ext.RolePermissionExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Repository
public class RolePermissionDao {
    @Autowired
    private RolePermissionExtMapper rolePermissionExtMapper;

    public void insertBatch(List<Integer> roleIdList, List<Integer> permissionIdList){
        rolePermissionExtMapper.insertBatch(roleIdList, permissionIdList);
    }
}
