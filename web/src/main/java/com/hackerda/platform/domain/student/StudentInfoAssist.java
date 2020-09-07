package com.hackerda.platform.domain.student;

/**
 * 学生登录的辅助信息，查询和记录黑白名单
 */
public interface StudentInfoAssist {

    boolean inLoginWhiteList(StudentAccount studentAccount);

    boolean needToCheckWechatCommentUser();
}
