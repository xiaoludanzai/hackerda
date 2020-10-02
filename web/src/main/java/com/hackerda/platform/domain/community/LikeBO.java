package com.hackerda.platform.domain.community;

import lombok.Data;

import java.util.Date;

/**
 * 点赞
 */
@Data
public class LikeBO {

    private Long id;

    private String userName;

    private IdentityCategory identityCategory;

    private String replyUserName;

    private long typeContentId;

    private LikeType likeType;

    private Date likeTime;

    private boolean show;

}
