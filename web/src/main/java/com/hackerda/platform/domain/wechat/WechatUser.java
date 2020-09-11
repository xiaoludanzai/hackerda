package com.hackerda.platform.domain.wechat;

import lombok.Data;

@Data
public class WechatUser {

    private final String appId;
    private final String openId;

    public WechatUser(String appId, String openId) {
        this.appId = appId;
        this.openId = openId;
    }
}
