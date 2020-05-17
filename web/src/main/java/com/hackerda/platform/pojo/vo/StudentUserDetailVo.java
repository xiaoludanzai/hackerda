package com.hackerda.platform.pojo.vo;

import com.hackerda.platform.pojo.Permission;
import com.hackerda.platform.pojo.Role;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.UserDetail;
import lombok.Data;

import java.util.Set;

/**
 * @author JR Chan
 */
@Data
public class StudentUserDetailVo {

    private Integer account;

    private String name;

    private String sex;

    private String ethnic;

    private String academyName;

    private String subjectName;

    private String className;

    private String token;

    private Set<Role> roleSet;

    private Set<Permission> permissionSet;

    public StudentUserDetailVo(StudentUser studentUser, UserDetail userDetail, String token){
        this.account = studentUser.getAccount();
        this.name = studentUser.getName();
        this.sex = studentUser.getSex();
        this.ethnic = studentUser.getEthnic();
        this.academyName = studentUser.getAcademyName();
        this.subjectName = studentUser.getSubjectName();
        this.className = studentUser.getClassName();
        this.roleSet = userDetail.getRoleSet();
        this.permissionSet = userDetail.getPermissionSet();
        this.token = token;
    }

}
