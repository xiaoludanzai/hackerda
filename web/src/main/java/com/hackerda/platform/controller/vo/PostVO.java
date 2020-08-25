package com.hackerda.platform.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class PostVO {

    private long id;

    /**
     * 用户id
     */
    private String userName;
    /**
     * 展示的用户名称
     */
    private String showUserName;
    /**
     * 展示的用户头像
     */
    private String avatar;

    private String content;

    private int identityCode;

    private List<ImageInfoVO> imageInfoList;

    /** 评论总数 **/
    private int commentCount = 0;

    /** 点赞数 **/
    private int likeCount = 0;

    /** 查看总数 **/
    private int viewCount = 0;

    /** 发表时间 **/
    private Date postTime;

    /** 是否允许评论 **/
    private boolean allowComment = true;

    private boolean hasLike;
}
