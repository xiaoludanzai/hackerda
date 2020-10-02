package com.hackerda.platform.domain.wechat;

import lombok.Data;

import java.io.Serializable;

@Data
public class WechatUser implements Serializable {

    private final String appId;
    private final String openId;

    public WechatUser(String appId, String openId) {
        this.appId = appId;
        this.openId = openId;
    }

    public String asValue() {
        return appId + ":" +openId;
    }
}
