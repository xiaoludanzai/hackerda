package com.hackerda.platform.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date postTime;

    /** 点赞数 **/
    private long likeCount = 0;

    private boolean hasLike;

    private boolean selfComment;
}
