package com.hackerda.platform.domain.community;

import com.hackerda.platform.domain.wechat.WechatUser;

import java.util.List;

public interface PostViewCounter {

    long increment(long postId, String userName, boolean isAnonymous, WechatUser wechatUser);

    long countPageViewByPost(long postId);

    long countUserViewByPost(long postId);

    List<Long> multiCountPageViewByPost(List<Long> postId);

    List<Long> multiCountUserViewByPost(List<Long> postId);

    /**
     * 用户所有圈子的pv总数
     * @param userName 用户名
     * @return pv总数
     */
    long countPageViewByUser(String userName);

    /**
     * 用户所有圈子的uv总数
     * @param userName 用户名
     * @return
     */
    long countUserViewByUser(String userName);
}
