package com.hackerda.platform.service.rbac;

import com.hackerda.platform.infrastructure.database.model.Permission;
import com.hackerda.platform.infrastructure.database.model.Role;

import java.util.List;

/**
 * 用户权限管理
 * @author JR Chan
 */
public interface UserAuthorityService {

    /**
     * 为角色赋予权限
     * @param role 角色
     * @param permissionList 权限列表
     */
    void grantRolePermission(Role role, List<Permission> permissionList);

    /**
     * 为学生赋予角色
     * @param account 学生学号
     * @param roleList 权限列表
     */
    void grantStudentRole(Integer account, List<Role> roleList);

    /**
     * 通过学号查看学生的角色
     * @param account 学号
     * @return 学生拥有的角色集合
     */
    List<Role> getRoleByStudent(String account);

    /**
     * 通过角色查看对应的权限
     * @param role 角色
     * @return 学生拥有的角色集合
     */
    List<Permission> getPermissionByRole(Role role);

    /**
     * 创建角色
     * @param roleName 角色的唯一中文名称
     * @param roleCode 角色的唯一英文编号
     * @return 创建成功的角色
     */
    Role createRole(String roleName, String roleCode);

    /**
     * 创建权限
     * @param permissionName 权限的唯一中文名称
     * @param permissionCode 权限的唯一英文编号
     * @return 创建成功的权限
     */
    Permission createPermission(String permissionName, String permissionCode);
}
