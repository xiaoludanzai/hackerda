package com.hackerda.platform.domain.community;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PostDetailBO extends PostBO {

    private final long id;

    /** 发布者信息 **/
    private Poster postUser;

    /** 评论总数 **/
    private int commentCount = 0;

    /** 点赞数 **/
    private int likeCount = 0;

    /** 查看总数 **/
    private int viewCount = 0;


    public PostDetailBO(long id, String userName, String content, List<ImageInfo> imageInfoList,
                        IdentityCategory identityCategory, String equipment) {
        super(userName, content, imageInfoList, identityCategory, equipment);
        this.id = id;
    }

    public PostDetailBO(long id, String userName, String content, List<ImageInfo> imageInfoList,
                        IdentityCategory identityCategory, Date postTime, String equipment) {
        super(userName, content, imageInfoList, identityCategory, postTime, equipment);
        this.id = id;
    }

    public String getShowUserName() {
        return postUser.getShowName(super.getIdentityCategory());
    }

    public String getShowAvatar() {
        return postUser.getShowAvatarUrl(super.getIdentityCategory());
    }
}
