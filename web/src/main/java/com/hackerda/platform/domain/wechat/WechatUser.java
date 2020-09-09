package com.hackerda.platform.domain.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WechatUser {

    private String appId;
    private String openId;
}
