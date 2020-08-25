package com.hackerda.platform.controller.vo;

import lombok.Data;

@Data
public class PostUserVO {
    /**
     * 用户id
     */
    private String userName;
    /**
     * 展示的用户名称
     */
    private String showUserName;

    /**
     * 展示的用户名称的排序
     *
     * 如果只有一个用户使用匿名身份  那么值对应的是0
     *
     * 如果同时有两个用户使用匿名身份  那么值分别为 1和 2
     */
    private int showUserNameOrder;
    /**
     * 展示的用户头像
     */
    private String avatar;



}
