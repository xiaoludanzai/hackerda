package com.hackerda.platform.domain.community;

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
    ;

    private final int code;

    RecordStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
