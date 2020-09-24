package com.hackerda.platform.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hackerda.platform.domain.community.Poster;
import lombok.Data;

import java.util.Date;

@Data
public class AppMessageVO {

    private long id;

    private PostUserVO sender;

    private String content;

    private String tagName;

    private int tagType;

    private boolean hasRead;

    private long postId;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
