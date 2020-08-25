package com.hackerda.platform.controller.vo;

import lombok.Data;

@Data
public class UserIdentityVO {

    private final int code;

    private final String showName;

    private final String avatarUrl;

    /**
     * 这个用户控制前端展示指默认选择哪个身份
     */
    private final boolean checked;


    public UserIdentityVO(int code, String showName, String avatarUrl, boolean checked) {
        this.code = code;
        this.showName = showName;
        this.avatarUrl = avatarUrl;
        this.checked = checked;
    }
}
