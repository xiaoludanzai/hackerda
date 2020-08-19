package com.hackerda.platform.domain.user;

import com.hackerda.platform.domain.community.IdentityCategory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserType {


    Student(0);

    private final int code;

    private static Map<Integer, UserType> codeMap;

    UserType(int code) {
        this.code = code;
    }

    public int getCode () {
        return this.code;
    }

    public static UserType getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(UserType.values())
                    .collect(Collectors.toMap(UserType::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
