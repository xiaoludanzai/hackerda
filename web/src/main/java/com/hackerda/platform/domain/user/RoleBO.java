package com.hackerda.platform.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class RoleBO {

    public static String USER = "user";

    private final String name;

    private final String code;

    private final List<PermissionBO> permissionList;


    public RoleBO(String name, String code) {
        this(name, code, new ArrayList<>(0));
    }

    public RoleBO(String name, String code, List<PermissionBO> permissionList) {
        this.name = name;
        this.code = code;
        this.permissionList = permissionList;
    }

    public void grantPermission(PermissionBO permissionBO) {
        permissionList.add(permissionBO);
    }

    public void grantPermission(List<PermissionBO> grantPermissionList) {
        permissionList.addAll(grantPermissionList);
    }

}
