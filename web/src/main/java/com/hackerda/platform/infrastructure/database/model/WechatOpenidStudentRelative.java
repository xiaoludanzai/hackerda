package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import lombok.Data;

/**
 * wechat_openid_student
 * @author 
 */
@Data
public class WechatOpenidStudentRelative implements Serializable {
    private Integer id;

    private String appid;

    private String openid;

    private Integer account;

    private static final long serialVersionUID = 1L;
}