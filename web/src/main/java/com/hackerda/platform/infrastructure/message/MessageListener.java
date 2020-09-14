package com.hackerda.platform.infrastructure.message;

import com.hackerda.platform.application.event.AddLikeEvent;
import com.hackerda.platform.application.event.CommentEvent;
import com.hackerda.platform.domain.community.CommentBO;
import com.hackerda.platform.domain.community.IdentityCategory;
import com.hackerda.platform.domain.community.LikeBO;
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

        MessageBO message = new MessageBO();
        message.setMessageSourceId(commentBO.getId());
        message.setMessageTriggerSource(MessageTriggerSource.Comment);
        message.setMessageType(MessageType.Notice);
        message.setReceiverUserName(commentBO.getReplyUserName());
        message.setSenderUserName(commentBO.getUserName());
        IdentityCategory category = commentBO.getIdentityCategory();

        message.setSenderIdentityCategory(category);
        message.setReceiverIdentityCategory(category);

        messageRepository.save(message);

    }


    @EventListener(value = AddLikeEvent.class)
    public void recordLikeMessage(AddLikeEvent event) {
        LikeBO likeBO = event.getLikeBO();

        MessageBO message = new MessageBO();
        message.setMessageSourceId(likeBO.getId());
        message.setMessageTriggerSource(MessageTriggerSource.Like);
        message.setMessageType(MessageType.Notice);
        message.setReceiverUserName(likeBO.getReplyUserName());
        message.setSenderUserName(likeBO.getUserName());

        message.setSenderIdentityCategory(IdentityCategory.Community);
        message.setReceiverIdentityCategory(IdentityCategory.Community);

        messageRepository.save(message);

    }

}
