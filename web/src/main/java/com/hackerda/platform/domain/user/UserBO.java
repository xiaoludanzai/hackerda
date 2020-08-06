package com.hackerda.platform.domain.user;

import java.util.List;

public class UserBO {

    private Integer id;
    /** 会员用户名 **/
    private String userName;
    /** 呢称 **/
    private String nickname;
    /** 密码 密码结构: sha256(sha256(密码)+[盐值])  **/
    private String password;
    /** 盐值 **/
    private String salt;

    /** 头像路径 不写入数据库**/
    private String avatarPath;

    /** 邮箱地址 **/
    private String email;
    /** 绑定手机 **/
    private String mobile;

    /** 用户性别  0：未知、1：男、2：女 **/
    private Integer gender;

    /** 个性签名 **/
    private String introduction;


    private List<RoleBO> roleList;

}
