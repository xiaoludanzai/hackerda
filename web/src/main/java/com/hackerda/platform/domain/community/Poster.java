package com.hackerda.platform.domain.community;

import lombok.Getter;

@Getter
public class Poster {

    /** 用户名称 **/
    private final String userName;

    /** 用户名称 **/
    private final String nickName;

    /** 用户名称 **/
    private final String avatarUrl;


    public Poster(String userName, String nickName, String avatarUrl) {
        this.userName = userName;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }

    public String getShowName(IdentityCategory identityCategory) {

        return identityCategory.getShowName(this);
    }


    public String getShowAvatarUrl(IdentityCategory identityCategory) {
        return identityCategory.getAvatarUrl(this);
    }
}
