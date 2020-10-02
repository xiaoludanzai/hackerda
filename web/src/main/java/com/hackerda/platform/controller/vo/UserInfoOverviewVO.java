package com.hackerda.platform.controller.vo;

import lombok.Data;

@Data
public class UserInfoOverviewVO {

    private AppUserVO userInfo;

    private long postCount;

    private long likeCount;

    private long viewCount;
}
