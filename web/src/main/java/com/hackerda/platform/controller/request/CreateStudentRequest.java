package com.hackerda.platform.controller.request;

import lombok.Data;

@Data
public class CreateStudentRequest {

    private String account;

    private String name;

    private String gender;

    private String clazzNum;

    private String openid;

    private String appId;
}
