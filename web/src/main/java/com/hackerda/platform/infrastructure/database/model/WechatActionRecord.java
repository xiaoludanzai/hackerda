package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * wechat_action_record
 * @author 
 */
@Data
public class WechatActionRecord implements Serializable {
    private Long id;

    private String appid;

    private String openid;

    private String account;

    private Integer action;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}