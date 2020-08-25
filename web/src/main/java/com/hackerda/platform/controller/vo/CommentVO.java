package com.hackerda.platform.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {

    private long id;

    private boolean isRoot;

    private long rootCommentId;

    private long replyCommentId;

    private long postId;

    /**
     * 发布该评论的用户
     */
    private PostUserVO postUser;

    /**
     * 被回复的用户
     */
    private PostUserVO replyUser;

    private String content;

    private Date postTime;

    /** 点赞数 **/
    private int likeCount = 0;

    private boolean hasLike;
}
