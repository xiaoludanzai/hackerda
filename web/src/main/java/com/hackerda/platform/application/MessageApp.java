package com.hackerda.platform.application;

import com.hackerda.platform.domain.message.AppNoticeMessageBO;
import com.hackerda.platform.domain.message.MessageFactory;
import com.hackerda.platform.domain.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageApp {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageFactory messageFactory;

    public void readPostMessage(String userName) {
        messageRepository.updateHasRead(userName);
    }


    public List<AppNoticeMessageBO> createAppNoticeByReceiver(String userName, Integer startId, int count,
                                                              boolean markAsRead) {

        List<AppNoticeMessageBO> messageList = messageFactory.createAppNoticeByReceiver(userName, startId, count);

        if(markAsRead) {
            messageRepository.updateHasRead(userName);
        }

        return messageList;
    }
}
