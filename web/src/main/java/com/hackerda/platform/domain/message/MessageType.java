package com.hackerda.platform.domain.message;


import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息的类型，譬如通知，私信，公告，提醒等
 */
@Getter
public enum MessageType {

    Notice(0)

    ;


    private final int code;

    private static Map<Integer, MessageType> codeMap;

    MessageType(int code) {
        this.code = code;
    }

    public static MessageType getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(MessageType.values())
                    .collect(Collectors.toMap(MessageType::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
