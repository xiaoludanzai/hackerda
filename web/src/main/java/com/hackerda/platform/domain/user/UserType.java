package com.hackerda.platform.domain.user;

public enum UserType {


    Student(0);

    private final int code;

    UserType(int code) {
        this.code = code;
    }

    public int getCode () {
        return this.code;
    }
}
