package com.hackerda.platform.pojo;

import java.util.Set;

/**
 * 用户的详细详细权限信息
 * @author JR Chan
 */
public interface UserDetail {

    /**
     * 获取用户的角色集合
     * @return 用户的角色集合
     */
    Set<Role> getRoleSet();

    /**
     * 获取用户的权限的集合
     * @return 获取用户的权限集合
     */
    Set<Permission> getPermissionSet();

}
