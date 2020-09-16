package com.hackerda.platform.infrastructure.repository.message;

import com.hackerda.platform.domain.community.IdentityCategory;
import com.hackerda.platform.domain.message.MessageBO;
import com.hackerda.platform.domain.message.MessageRepository;
import com.hackerda.platform.domain.message.MessageTriggerSource;
import com.hackerda.platform.domain.message.MessageType;
import com.hackerda.platform.infrastructure.database.mapper.ext.MessageExtMapper;
import com.hackerda.platform.infrastructure.database.model.Message;
import com.hackerda.platform.infrastructure.database.model.MessageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @Autowired
    private MessageExtMapper messageExtMapper;


    @Override
    public void save(MessageBO messageBO) {

        Message record = toDO(messageBO);

        messageExtMapper.insertSelective(record);

        messageBO.setId(record.getId());

    }

    @Override
    public void update(MessageBO messageBO) {

        if(messageBO.getId() == null) {
            throw new RuntimeException("消息id为空,无法更新");
        }

        Message record = toDO(messageBO);
        record.setId(messageBO.getId());

        messageExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<MessageBO> findByUserName(String userName, Integer startId, int count) {
        MessageExample example = new MessageExample();
        example.createCriteria().andReceiverUserNameEqualTo(userName);

        return messageExtMapper.selectByReceiverUser(userName, startId, count).stream().map(this::toBO).collect(Collectors.toList());
    }

    private Message toDO(MessageBO messageBO) {
        Message record = new Message();

        record.setHasRead(messageBO.isHasRead() ? (byte) 1 : (byte) 0);

        record.setMessageSourceId(messageBO.getMessageSourceId());
        record.setMessageTypeCode(messageBO.getMessageType().getCode());

        record.setMessageTriggerSourceCode(messageBO.getMessageTriggerSource().getCode());

        record.setSenderUserName(messageBO.getSenderUserName());
        record.setSenderIdentityCategoryCode(messageBO.getSenderIdentityCategory().getCode());

        record.setReceiverUserName(messageBO.getReceiverUserName());
        record.setReceiverIdentityCategoryCode(messageBO.getReceiverIdentityCategory().getCode());

        return record;

    }

    private MessageBO toBO(Message message) {

        return MessageBO.builder()
                .id(message.getId())
                .hasRead(message.getHasRead() == (byte) 1)
                .messageSourceId(message.getMessageSourceId())
                .messageType(MessageType.getByCode(message.getMessageTypeCode()))
                .messageTriggerSource(MessageTriggerSource.getByCode(message.getMessageTriggerSourceCode()))
                .senderUserName(message.getSenderUserName())
                .senderIdentityCategory(IdentityCategory.getByCode(message.getSenderIdentityCategoryCode()))
                .receiverUserName(message.getReceiverUserName())
                .receiverIdentityCategory(IdentityCategory.getByCode(message.getReceiverIdentityCategoryCode()))
                .createTime(message.getGmtCreate())
                .build();
    }
}
