package com.hackerda.platform.controller.request;

import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long postId;

    private String postUserName;

    private Long replyCommentId;

    private Long rootCommentId;

    private String content;

    private ImageInfoRequest imageInfoRequestList;

    private Integer identityCode;
}
