package com.hackerda.platform.infrastructure.repository.message;

import com.github.pagehelper.PageHelper;
import com.hackerda.platform.domain.community.IdentityCategory;
import com.hackerda.platform.domain.community.RecordStatus;
import com.hackerda.platform.domain.message.MessageBO;
import com.hackerda.platform.domain.message.MessageRepository;
import com.hackerda.platform.domain.message.MessageTriggerSource;
import com.hackerda.platform.domain.message.MessageType;
import com.hackerda.platform.infrastructure.database.mapper.ext.MessageExtMapper;
import com.hackerda.platform.infrastructure.database.model.Message;
import com.hackerda.platform.infrastructure.database.model.MessageExample;
import com.hackerda.platform.infrastructure.database.model.PostExample;
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
    public List<MessageBO> findReleaseByUserName(String userName, Integer startId, int count) {
        MessageExample example = new MessageExample();
        example.setOrderByClause("id desc");
        MessageExample.Criteria criteria = example.createCriteria();
        if(startId != null) {
            criteria.andIdLessThan(startId.longValue());
        }

        criteria.andRecordStatusEqualTo(RecordStatus.Release.getCode());
        PageHelper.startPage(0, count);

        return messageExtMapper.selectByExample(example).stream().map(this::toBO).collect(Collectors.toList());
    }

    @Override
    public List<MessageBO> find(MessageTriggerSource triggerSource, long messageSourceId) {
        MessageExample example = new MessageExample();
        example.createCriteria()
                .andMessageSourceIdEqualTo(messageSourceId)
                .andMessageTriggerSourceCodeEqualTo(triggerSource.getCode());


        return messageExtMapper.selectByExample(example).stream().map(this::toBO).collect(Collectors.toList());
    }

    @Override
    public void updateHasRead(String receiveUserName) {
        MessageExample example = new MessageExample();
        example.createCriteria().andReceiverUserNameEqualTo(receiveUserName);
        Message record = new Message();
        record.setHasRead((byte) 1);
        messageExtMapper.updateByExampleSelective(record, example);
    }

    @Override
    public long countHasNotRead(String receiveUserName) {
        MessageExample example = new MessageExample();
        example.createCriteria()
                .andReceiverUserNameEqualTo(receiveUserName)
                .andHasReadEqualTo((byte) 0);
        return messageExtMapper.countByExample(example);
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

        record.setRecordStatus(messageBO.getRecordStatus().getCode());

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
                .recordStatus(RecordStatus.getByCode(message.getRecordStatus()))
                .build();
    }
}
