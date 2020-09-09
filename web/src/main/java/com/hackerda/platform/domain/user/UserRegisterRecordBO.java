package com.hackerda.platform.domain.user;

import com.hackerda.platform.domain.wechat.WechatUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class UserRegisterRecordBO {

    @Setter
    private Long id;

    private final String userName;

    private final WechatUser wechatUser;

    private final PhoneNumber phoneNumber;

    @Setter
    private LifeCycleStatus lifeCycleStatus;

    public UserRegisterRecordBO(String userName, WechatUser wechatUser, PhoneNumber phoneNumber, LifeCycleStatus lifeCycleStatus) {
        this.id = null;
        this.userName = userName;
        this.wechatUser = wechatUser;
        this.phoneNumber = phoneNumber;
        this.lifeCycleStatus = lifeCycleStatus;
    }
}
