package com.hackerda.platform.controller.request;

import lombok.Data;

@Data
public class LikeRequest {

    /**
     * 0 表示post  1表示评论
     */
    private Integer contentType;

    /**
     * contentType为0时表示postId  contentType为1表示评论id
     */
    private Long contentId;

    /**
     * true为添加  false为删除
     */
    private boolean isAdd;
}
