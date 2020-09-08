package com.hackerda.platform.controller.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 来自学生的用户创建请求
 */

@Data
public class CreateUserByStudentRequest {

    @NotEmpty
    private String studentAccount;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String phoneNumber;

    @NotNull
    private Integer gender;

    private String signature;

    @NotEmpty
    private String avatarUrl;

    private String openId;

    private String appId;



}
