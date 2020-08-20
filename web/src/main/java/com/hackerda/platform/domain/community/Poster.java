package com.hackerda.platform.domain.community;

import com.hackerda.platform.domain.user.Gender;
import lombok.Getter;

@Getter
public class Poster {

    /** 用户名称 **/
    private final String userName;

    /** 用户名称 **/
    private final String nickName;

    /** 用户名称 **/
    private final String avatarUrl;

    /** 性别 **/
    private final Gender gender;

    public Poster(String userName, String nickName, String avatarUrl, Gender gender) {
        this.userName = userName;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
    }

    public String getShowName(IdentityCategory identityCategory) {

        return identityCategory.getShowName(this);
    }


    public String getShowAvatarUrl(IdentityCategory identityCategory) {
        return identityCategory.getAvatarUrl(this);
    }
}
