package com.hackerda.platform.domain.wechat;

import com.hackerda.platform.domain.student.Action;

import java.util.List;

public interface WechatActionRecordRepository {

    void save(ActionRecord actionRecord);

    List<ActionRecord> find(WechatUser wechatUser);

    List<ActionRecord> find(WechatUser wechatUser, Action action);
}
