package com.hackerda.platform.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsExclude;

@Getter
@ToString
@EqualsAndHashCode
public class PermissionBO {

    public static final String VIEW = "view";

    public static final String COMMENT = "comment";

    public static final String DELETE = "delete";

    private final String name;

    private final String code;

    /**
     * 是否是新的权限，区别从数据库中读取还是用户自己新建
     */
    @EqualsExclude
    private final boolean isNew;


    public PermissionBO(String name, String code, boolean isNew) {
        this.name = name;
        this.code = code;
        this.isNew = isNew;
    }

}
