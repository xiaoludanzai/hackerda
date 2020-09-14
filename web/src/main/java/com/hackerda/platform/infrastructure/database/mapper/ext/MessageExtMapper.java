package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.MessageMapper;
import com.hackerda.platform.infrastructure.database.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageExtMapper extends MessageMapper {

    List<Message> selectByReceiverUser(String receiverUserName, Integer startId,  int count);
}
