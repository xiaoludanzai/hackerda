package com.hackerda.platform.domain.user;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum LogoutType {

    USER_LOGOUT("用户注销", 0),


    /**
     * 违规用户，譬如冒用他人账号被注销
     */
    ILLEGAL_USER("系统注销", 1),

    TEST_USER("测试账号", 2),

    ;

    private final String source;
    private final int code;

    private static Map<Integer, LogoutType> codeMap;

    LogoutType(String source, int code) {
        this.source = source;
        this.code = code;
    }

    public static LogoutType getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(LogoutType.values())
                    .collect(Collectors.toMap(LogoutType::getCode, x -> x));
        }

        return codeMap.get(code);
    }

}
