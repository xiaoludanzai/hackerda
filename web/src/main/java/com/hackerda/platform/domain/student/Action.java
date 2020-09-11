package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.user.LogoutType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static Map<Integer, Action> codeMap;

    Action(int code) {
        this.code = code;
    }

    public static Action getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(Action.values())
                    .collect(Collectors.toMap(Action::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
