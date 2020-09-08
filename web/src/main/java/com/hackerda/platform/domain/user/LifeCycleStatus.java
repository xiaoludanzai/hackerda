package com.hackerda.platform.domain.user;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum LifeCycleStatus {

    /**
     * 创建中
     */
    Creating(0),

    /**
     * 创建失败
     */
    CreateFail(1),

    /**
     * 正常状态
     */
    Normal(2),

    /**
     * 注销状态
     */
    Logout(3),

    ;

    private final int code;

    private static Map<Integer, LifeCycleStatus> codeMap;

    LifeCycleStatus(int code) {
        this.code = code;
    }

    public static LifeCycleStatus getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(LifeCycleStatus.values())
                    .collect(Collectors.toMap(LifeCycleStatus::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
