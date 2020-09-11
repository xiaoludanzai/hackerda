package com.hackerda.platform.domain.wechat;

import com.hackerda.platform.domain.student.Action;
import com.hackerda.platform.domain.student.StudentAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class ActionRecord {

    private final WechatUser wechatUser;

    private final Action action;

    private final StudentAccount studentAccount;

    private final Date gmtCreate;

    private final Date gmtModify;

    public ActionRecord(WechatUser wechatUser, Action action, StudentAccount studentAccount) {
        this.wechatUser = wechatUser;
        this.action = action;
        this.studentAccount = studentAccount;
        this.gmtCreate = new Date();
        this.gmtModify = new Date();
    }
}
