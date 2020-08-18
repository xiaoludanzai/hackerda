package com.hackerda.platform.controller.vo;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostIdentityVO {


    private final List<Item> itemList = new ArrayList<>(0);

    private int count;


    public void add (int code, String showName, String avatarUrl, boolean checked) {
        itemList.add(new Item(code,showName, avatarUrl, checked));
        count += 1;
    }


    @Getter
    public static class Item {
        private final int code;

        private final String showName;

        private final String avatarUrl;

        /**
         * 这个用户控制前端展示指默认选择哪个身份
         */
        private final boolean checked;



        public Item(int code, String showName, String avatarUrl, boolean checked) {
            this.code = code;
            this.showName = showName;
            this.avatarUrl = avatarUrl;
            this.checked = checked;
        }
    }

}
