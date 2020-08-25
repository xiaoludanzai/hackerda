package com.hackerda.platform.domain.community;

public enum LikeType {

    Post(0),

    Comment(1)

    ;

    private final int code;

    LikeType(int i) {
        this.code = i;
    }


    public int getCode() {
        return code;
    }

    public static LikeType getByCode(int code){
        if (code == 0) {
            return Post;
        }
        return Comment;
    }
}
