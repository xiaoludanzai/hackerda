package com.hackerda.platform.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class PostDetailVO {

    private long maxId;

    private int count;

    private List<PostVO> postList;

}
