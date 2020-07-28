package com.hackerda.platform.service.rbac;

import com.hackerda.platform.infrastructure.dao.StudentUserDao;
import com.hackerda.platform.infrastructure.dao.rbac.PermissionDao;
import com.hackerda.platform.infrastructure.dao.rbac.RoleDao;
import com.hackerda.platform.infrastructure.dao.rbac.RolePermissionDao;
import com.hackerda.platform.infrastructure.dao.rbac.StudentRoleDao;
import com.hackerda.platform.pojo.Permission;
import com.hackerda.platform.pojo.Role;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JR Chan
 */
@Service
public class UserAuthorityServiceImpl implements UserAuthorityService{
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private StudentRoleDao studentRoleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private StudentUserDao studentUserDao;

    @Override
    public void grantRolePermission(Role role, List<Permission> permissionList) {
        rolePermissionDao.insertBatch(Lists.newArrayList(role.getId()), permissionList.stream().map(Permission::getId).collect(Collectors.toList()));
    }

    @Override
    public void grantStudentRole(Integer account, List<Role> roleList) {
        studentRoleDao.insertBatch(account, roleList.stream().map(Role::getId).collect(Collectors.toList()));
    }

    @Override
    public List<Role> getRoleByStudent(String account) {
        return studentUserDao.selectRoleByAccount(account);
    }

    @Override
    public List<Permission> getPermissionByRole(Role role) {
        return roleDao.selectPermissionById(role.getId());
    }

    @Override
    public Role createRole(String roleName, String roleCode) {
        Role role = new Role().setName(roleName).setCode(roleCode);
        roleDao.insertSelective(role);
        return role;
    }

    @Override
    public Permission createPermission(String permissionName, String permissionCode) {
        Permission permission = new Permission().setPermissionName(permissionName).setPermissionCode(permissionCode);
        permissionDao.insertSelective(permission);
        return permission;
    }
}
