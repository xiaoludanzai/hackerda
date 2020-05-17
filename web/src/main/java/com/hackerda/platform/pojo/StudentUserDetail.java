package com.hackerda.platform.pojo;

import java.util.Set;

/**
 * @author JR Chan
 */
public class StudentUserDetail implements UserDetail{

    private StudentUser studentUser;

    private Set<Role> roleSet;

    private Set<Permission> permissionSet;


    @Override
    public Set<Role> getRoleSet() {
        return roleSet;
    }

    @Override
    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
