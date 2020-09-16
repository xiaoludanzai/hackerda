package com.hackerda.platform.domain.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 评论
 *
 * 回复也是评论的一种
 */
@Getter
@ToString
public class CommentBO {

    @Setter
    private Long id;

    private final String userName;

    private final long postId;

    private final String postUserName;

    /**
     * 被回复的评论id，如果为是回复帖子的评论，则为0
     */
    private final long replyCommentId;


    /**
     * 最顶层评论的id 如果为是回复帖子的评论，则为0
     */
    private final long rootCommentId;

    private ImageInfo imageInfo;

    /** 用户身份 **/
    private final IdentityCategory identityCategory;

    @Setter
    private RecordStatus status;

    /** 评论内容 **/
    private final String content;
    /** 评论时间 **/
    private final Date postTime;

    @Setter
    private String replyUserName;

    public CommentBO(long postId, String postUserName, String userName, String content, long replyCommentId,
                     long rootCommentId,
                     IdentityCategory identityCategory, String replyUserName) {
        this.postId = postId;
        this.postUserName = postUserName;
        this.userName = userName;
        this.content = content;
        this.identityCategory = identityCategory;
        this.rootCommentId  = rootCommentId;
        this.postTime = new Date();
        this.status = RecordStatus.Create;
        this.replyCommentId = replyCommentId;
        this.replyUserName = replyUserName;

    }


    public CommentBO(long postId, String postUserName, String userName, String content, long replyCommentId,
                     long rootCommentId, Date postTime, RecordStatus status,
                     IdentityCategory identityCategory, String replyUserName) {
        this.postId = postId;
        this.postUserName = postUserName;
        this.userName = userName;
        this.content = content;
        this.identityCategory = identityCategory;
        this.rootCommentId  = rootCommentId;
        this.postTime = postTime;
        this.status = status;
        this.replyCommentId = replyCommentId;
        this.replyUserName = replyUserName;
    }

    public boolean isRelease() {
        return status == RecordStatus.Release;
    }

    public String getUnReleaseReason() {
        if(status != RecordStatus.Release) {
            return "包含违规内容，校验不通过";
        }else {
            return "";
        }
    }

    public boolean isRoot() {
        return rootCommentId == 0L;
    }
}
