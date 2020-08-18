package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * post
 * @author 
 */
@Data
public class Post implements Serializable {
    private Long id;

    private String userName;

    private String content;

    private Byte allowComment;

    private Integer identityCode;

    private Date postTime;

    private Integer status;

    private Integer commentCount;

    private Integer likeCount;

    private Integer viewCount;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}