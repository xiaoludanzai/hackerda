package com.hackerda.platform.domain.message;

import java.util.List;

public interface MessageRepository {


    void save(MessageBO messageBO);

    void update(MessageBO messageBO);

    List<MessageBO> findByUserName(String userName, Integer startId, int count);
}
