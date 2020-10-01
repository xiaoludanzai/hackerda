package com.hackerda.platform.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ModifyUserInfoRequest {

    @NotEmpty
    private String nickName;

    private String phoneNumber;

    @NotNull
    private Integer gender;

    private String signature;

    @NotEmpty
    private String avatarUrl;

    @NotEmpty
    private String openId;

    @NotEmpty
    private String appId;
}
