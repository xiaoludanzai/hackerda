package com.hackerda.platform.domain.user;

import com.hackerda.platform.domain.wechat.WechatUser;

public interface UserRegisterAssist {

    /**
     * 检查该小程序学生用户能否注册
     * 校验的事项
     * 1.该用户的学号是否已经注册了科大圈用户身份
     * 2.改用户的手机号已经被其它账户注册
     *
     * @param appStudentUserBO 小程序学生用户
     * @return 如果可以注册则返回ture
     */
    boolean userCanRegister(AppStudentUserBO appStudentUserBO);

    /**
     * 校验该微信来源的用户能否注册科大圈
     * 原则上一个微信号只能注册一个科大圈账号
     *
     * 1. 未注册过科大圈的微信可以注册
     * 2. 在白名单的微信用户可以多次注册
     *
     * @param wechatUser 微信用户
     * @return 可以注册则返回true
     */
    boolean wechatCanRegister(WechatUser wechatUser);
}
