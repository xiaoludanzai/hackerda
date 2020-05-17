package com.hackerda.platform.pojo.wechat.miniprogram;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class AuthResponse extends Response {

    private String openid;

    @SerializedName("session_key")
    private String sessionKey;

    @SerializedName("unionid")
    private String unionId;

}
