package com.hackerda.platform.domain.message;

import com.hackerda.platform.domain.community.IdentityCategory;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息的触发来源，譬如点赞，评论，系统生成
 */
@Getter
public enum MessageTriggerSource {


    Like(0),

    Comment(1)
    ;


    private final int code;

    private static Map<Integer, MessageTriggerSource> codeMap;

    MessageTriggerSource(int code) {
        this.code = code;
    }

    public static MessageTriggerSource getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(MessageTriggerSource.values())
                    .collect(Collectors.toMap(MessageTriggerSource::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
