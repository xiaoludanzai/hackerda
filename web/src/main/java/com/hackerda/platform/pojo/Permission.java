package com.hackerda.platform.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Permission {
    private Integer id;

    private String permissionName;

    private String permissionCode;
}