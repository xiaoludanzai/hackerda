package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.wechat.WechatUser;

/**
 * 学生登录的辅助信息，查询和记录黑白名单
 */
public interface StudentInfoAssist {

    boolean needToCheckWechatCommentUser();

    /**
     * 检查微信号是否是该学号的常用微信
     * 是常用微信的条件满足其中一个即可
     *
     * 1.微信号与该学号注册时使用的微信号一致
     * 2.微信号曾经使用手机号授权登录过该学号
     *
     *
     * @param account 学号
     * @param wechatUser 微信用户
     * @return
     */
    boolean isCommonWechat(StudentAccount account, WechatUser wechatUser);
}
