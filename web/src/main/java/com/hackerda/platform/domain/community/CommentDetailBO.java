package com.hackerda.platform.domain.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString(callSuper = true)
public class CommentDetailBO extends CommentBO {

    private final long id;

    private final Poster poster;

    /** 点赞数目 **/
    private final int likeCount;

    @Setter
    private int userShowNameOrder;


    public CommentDetailBO(long id, long postId, String postUserName, Poster poster, String content,
                           long replyCommentId, long rootCommentId, Date postTime, RecordStatus status,
                           IdentityCategory identityCategory, int likeCount) {
        super(postId, postUserName, poster.getUserName(), content, replyCommentId, rootCommentId, postTime, status,
                identityCategory);

        this.id = id;
        this.likeCount = likeCount;
        this.poster = poster;
    }


    public String getShowUserName() {
        return poster.getShowName(this.getIdentityCategory());
    }

    public String getShowAvatar() {
        return poster.getShowAvatarUrl(this.getIdentityCategory());
    }

    /**
     * 由于在插入的时候没有评论id，所以写入表的时候这个数据是O
     * 这个方法提供给根评论聚合使用
     * @return
     */
    public long getRootCommentId() {
        return isRoot() ? this.id : super.getRootCommentId();
    }
}
