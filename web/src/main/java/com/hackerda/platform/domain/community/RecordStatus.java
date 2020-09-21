package com.hackerda.platform.domain.community;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子和评论的状态
 */
public enum RecordStatus {

    /**
     * 已发布
     */
    Release(0),

    /**
     * 删除
     */
    Delete(1),

    /**
     * 审核中
     */
    UnderReview(2),

    /**
     * 审核不通过
     */
    UnPassReview(3),

    /**
     * 审核不通过
     */
    Create(4),

    /**
     * 隐藏
     */
    Hide(5),
    ;

    private final int code;

    private static Map<Integer, RecordStatus> codeMap;

    RecordStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RecordStatus getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(RecordStatus.values())
                    .collect(Collectors.toMap(RecordStatus::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
