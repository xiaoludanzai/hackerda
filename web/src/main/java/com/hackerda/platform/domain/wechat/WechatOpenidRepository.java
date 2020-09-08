package com.hackerda.platform.domain.wechat;

import com.hackerda.platform.domain.student.StudentWechatBindDetail;

public interface WechatOpenidRepository {

    StudentWechatBindDetail findBindByAccount(String account);

    StudentWechatBindDetail findByOpenId(String account);
}
