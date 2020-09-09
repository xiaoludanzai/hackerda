package com.hackerda.platform.domain.user;

import lombok.Getter;

@Getter
public enum LogoutType {

    USER_LOGOUT("用户注销", 0),


    /**
     * 违规用户，譬如冒用他人账号被注销
     */
    ILLEGAL_USER("系统注销", 1),

    ;

    private final String source;
    private final int code;

    LogoutType(String source, int code) {
        this.source = source;
        this.code = code;
    }

}
