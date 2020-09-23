package com.hackerda.platform.domain.message;

import com.hackerda.platform.domain.community.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
public class AppNoticeMessageBO {

    @Getter
    private final Long id;


    private final Poster senderUser;

    private final IdentityCategory senderIdentityCategory;

    private final Poster receiverUser;

    private final IdentityCategory receiverIdentityCategory;

    @Getter
    private final MessageTriggerSource messageTriggerSource;

    private final MessageType messageType;

    private final LikeBO sourceLike;

    private final PostBO sourcePost;

    private final CommentBO sourceComment;

    @Getter
    private final long postId;

    @Getter
    private final boolean hasRead;

    @Getter
    private final Date createTime;


    public String getContent() {

        if(messageTriggerSource == MessageTriggerSource.Comment) {
            return sourceComment.getContent();
        }

        if(messageTriggerSource == MessageTriggerSource.Like) {

            if(sourceLike.getLikeType() == LikeType.Comment) {

                return sourceComment.getContent();
            }

            if(sourceLike.getLikeType() == LikeType.Post) {
                if(sourcePost.hasContent()) {
                    return sourcePost.getContent();
                }
                return "图片分享";
            }

        }
        throw new RuntimeException("unSupport type");
    }

    public String getTagName() {
        if(hasRead) {
            return "朕已阅";
        }

        if(messageTriggerSource == MessageTriggerSource.Comment) {
            if(sourceComment.isRoot()) {
                return "评论了你";
            }
            return "回复了你";
        }

        if(messageTriggerSource == MessageTriggerSource.Like) {
            return "赞了你";
        }
        throw new RuntimeException("unSupport type");

    }

    public String getSenderAvatar() {
        return senderUser.getShowAvatarUrl(senderIdentityCategory);
    }

    public String getSenderUserName() {
        return senderUser.getUserName();
    }

    public String getSenderShowName() {
        return senderUser.getShowName(senderIdentityCategory);
    }

    public boolean contentHasDelete() {
        if(messageTriggerSource == MessageTriggerSource.Comment) {
            return sourceComment.hasDelete();
        }

        if(messageTriggerSource == MessageTriggerSource.Like) {

            if(sourceLike.getLikeType() == LikeType.Comment) {
                return sourceComment.hasDelete();
            }

            if(sourceLike.getLikeType() == LikeType.Post) {
                return sourcePost.isDelete();
            }
        }
        throw new RuntimeException("unSupport type");
    }

}
