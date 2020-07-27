package com.hackerda.platform.domain.wechat;

import com.hackerda.platform.domain.student.WechatOpenidBO;

public interface WechatOpenidRepository {

    WechatOpenidBO findBindByAccount(String account);

    WechatOpenidBO findByOpenId(String account);
}
