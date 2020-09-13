package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * comment
 * @author 
 */
@Data
public class Comment implements Serializable {
    private Long id;

    private Long postId;

    private String postUserName;

    private String userName;

    private String content;

    private Integer likeCount;

    private Long replyCommentId;

    private String replyUserName;

    private Long rootCommentId;

    private Integer identityCode;

    private Integer recordStatus;

    private Date postTime;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}