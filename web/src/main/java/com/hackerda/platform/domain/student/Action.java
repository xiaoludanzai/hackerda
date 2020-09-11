package com.hackerda.platform.domain.student;

import lombok.Getter;

@Getter
public enum Action {

    Login(0),

    AuthLogin(1),

    SignOut(2),

    PasswordUnCorrect(3),

    UnCommonWechat(4),

    AccountHasBind(5),

    ;


    private final int code;
    Action(int code) {
        this.code = code;
    }
}
