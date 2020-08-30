package com.hackerda.platform.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonWechat {

    private String appId;
    private String openId;
}
