package com.hackerda.platform.domain.community;

import java.util.Date;
import java.util.List;

public class PostDetailBO extends PostBO {

    /** 发布者信息 **/
    private Poster postUser;

    /** 评论总数 **/
    private Long commentTotal = 0L;

    /** 点赞列表 **/
    private List<Like> likeList;

    /** 查看总数 **/
    private Long viewTotal = 0L;


    public PostDetailBO(String userName, String content, List<ImageInfo> imageInfoList, IdentityCategory identityCategory) {
        super(userName, content, imageInfoList, identityCategory);
    }

    public PostDetailBO(String userName, String content, List<ImageInfo> imageInfoList, IdentityCategory identityCategory, Date postTime) {
        super(userName, content, imageInfoList, identityCategory, postTime);
    }
}
