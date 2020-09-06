package com.hackerda.platform.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class CreatePostRequest {

    private String content;

    private List<ImageInfoRequest> imageInfoRequestList;

    private Integer identityCode;

    private String equipment;
}
