package com.hackerda.platform.domain.message;

import java.util.List;

public interface MessageRepository {


    void save(MessageBO messageBO);

    void update(MessageBO messageBO);

    List<MessageBO> findReleaseByUserName(String userName, Integer startId, int count);

    List<MessageBO> find(MessageTriggerSource triggerSource, long messageSourceId);

    /**
     * 将接受者的所有消息记录更新为已读
     * 正常情况下的设计应该是根据接受者读出所有的消息然后置为已读
     * 这个方法是个为了效率的折衷，并不符合面向对象的思想
     *
     * @param receiveUserName 信息接受者的用户名
     */
    void updateHasRead(String receiveUserName);

    long countHasNotRead(String receiveUserName);
}
