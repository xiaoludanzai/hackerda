package com.hackerda.platform.controller.vo;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostIdentityVO {


    private final List<UserIdentityVO> itemList = new ArrayList<>(0);

    private int count;


    public void add (int code, String showName, String avatarUrl, boolean checked) {
        itemList.add(new UserIdentityVO(code,showName, avatarUrl, checked));
        count += 1;
    }

}
