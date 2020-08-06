package com.hackerda.platform.domain.community;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 帖子
 */

public class Post {

    private Long id;
    /** 标题 **/
    private String title;

    /** 发布者信息 **/
    private PostUser postUser;

    /** 标签Id **/
    private Long tagId;
    /** 标签名称 **/
    private String tagName;

    /** 话题内容 **/
    private String content;

    /** 发表时间 **/
    private Date postTime = new Date();
    /** 最后回复时间 **/
    private Date lastReplyTime;

    /** 图片文件信息集合 **/
    private List<ImageInfo> imageInfoList = new ArrayList<>();

    /** 媒体文件信息集合 **/
    private List<MediaInfo> mediaInfoList = new ArrayList<>();

    /** 评论总数 **/
    private Long commentTotal = 0L;
    /** 允许评论 **/
    private boolean allowComment = true;

    /** 点赞列表 **/
    private List<Like> likeList;

    /** 查看总数 **/
    private Long viewTotal = 0L;

    /** 状态 10.待审核 20.已发布 110.待审核删除 120.已发布删除 **/
    private Integer status = 10;
}
