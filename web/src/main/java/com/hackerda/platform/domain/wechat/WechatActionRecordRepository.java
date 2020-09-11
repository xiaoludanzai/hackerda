package com.hackerda.platform.domain.wechat;

import java.util.List;

public interface WechatActionRecordRepository {

    void save(ActionRecord actionRecord);

    List<ActionRecord> find(WechatUser wechatUser);
}
