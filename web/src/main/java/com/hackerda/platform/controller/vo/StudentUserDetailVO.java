package com.hackerda.platform.controller.vo;

import com.hackerda.platform.infrastructure.database.model.Permission;
import com.hackerda.platform.infrastructure.database.model.Role;
import lombok.Data;

import java.util.Set;

/**
 * @author JR Chan
 */
@Data
public class StudentUserDetailVO {

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

}
