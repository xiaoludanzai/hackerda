package com.hackerda.platform.controller.vo;

import lombok.Data;

@Data
public class AppUserVO {


    /** 呢称 **/
    private String nickname;

    /** 头像路径 不写入数据库**/
    private String avatarPath;

    /** 邮箱地址 **/
    private String email;
    /** 绑定手机 **/
    private String mobile;

    /** 用户性别  0：未知、1：男、2：女 **/
    private String gender;

    /** 个性签名 **/
    private String introduction;

    private String userType;

    private boolean useDefaultPassword;

    private String token;
}
