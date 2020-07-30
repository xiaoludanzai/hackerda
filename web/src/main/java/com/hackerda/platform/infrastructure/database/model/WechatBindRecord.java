package com.hackerda.platform.infrastructure.database.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class WechatBindRecord {
    private Integer id;

    private String openid;

    private String appid;

    private String originAccount;

    private String updateAccount;

    private Date gmtCreate;

}