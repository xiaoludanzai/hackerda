package com.hackerda.platform.infrastructure.message;

import com.hackerda.platform.application.event.AddLikeEvent;
import com.hackerda.platform.application.event.CommentEvent;
import com.hackerda.platform.domain.community.CommentBO;
import com.hackerda.platform.domain.community.IdentityCategory;
import com.hackerda.platform.domain.community.LikeBO;
import com.hackerda.platform.domain.community.RecordStatus;
import com.hackerda.platform.domain.message.MessageBO;
import com.hackerda.platform.domain.message.MessageRepository;
import com.hackerda.platform.domain.message.MessageTriggerSource;
import com.hackerda.platform.domain.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private MessageRepository messageRepository;

    @EventListener(value = CommentEvent.class)
    public void recordCommentMessage(CommentEvent event) {
        CommentBO commentBO = event.getCommentBO();

        IdentityCategory category = commentBO.getIdentityCategory();
        MessageBO messageBO = MessageBO.builder()
                .messageSourceId(commentBO.getId())
                .messageTriggerSource(MessageTriggerSource.Comment)
                .messageType(MessageType.Notice)
                .receiverUserName(commentBO.getReplyUserName())
                .receiverIdentityCategory(category)
                .senderUserName(commentBO.getUserName())
                .senderIdentityCategory(category)
                .hasRead(false)
                .recordStatus(RecordStatus.Release)
                .build();

        if(!messageBO.isTriggerBySelf()) {
            messageRepository.save(messageBO);
        }

    }


    @EventListener(value = AddLikeEvent.class)
    public void recordLikeMessage(AddLikeEvent event) {
        LikeBO likeBO = event.getLikeBO();

        IdentityCategory category = IdentityCategory.Community;

        MessageBO messageBO = MessageBO.builder()
                .messageSourceId(likeBO.getId())
                .messageTriggerSource(MessageTriggerSource.Like)
                .messageType(MessageType.Notice)
                .receiverUserName(likeBO.getReplyUserName())
                .receiverIdentityCategory(category)
                .senderUserName(likeBO.getUserName())
                .senderIdentityCategory(category)
                .hasRead(false)
                .recordStatus(RecordStatus.Release)
                .build();

        if(!messageBO.isTriggerBySelf()) {
            messageRepository.save(messageBO);
        }

    }

}
