package com.hackerda.platform.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class AppMessageOverviewVO {

    private long nextMaxId;

    private int count;

    private List<AppMessageVO> messageList;
}
