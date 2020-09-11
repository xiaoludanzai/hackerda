package com.hackerda.platform.domain.wechat;

import com.hackerda.platform.domain.student.Action;
import com.hackerda.platform.domain.student.StudentAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ActionRecord {

    private final WechatUser wechatUser;

    private final Action action;

    private final StudentAccount studentAccount;
}
