package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;

@Data
public class RoleDetailDO {

    private String roleName;
    private String roleCode;

    private String permissionName;
    private String permissionCode;
}
