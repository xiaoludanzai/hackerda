package com.hackerda.platform.domain.user;

/**
 * 性别
 */
public enum Gender {


    UnKnow(0, "未知"),

    Man(1, "男"),

    Woman(2 ,"女");

    private final int code;

    private final String desc;

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Gender formCode(int code) {
        switch (code){
            case 1:
                return Man;
            case 2:
                return Woman;
            default:
                return UnKnow;
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }
}
