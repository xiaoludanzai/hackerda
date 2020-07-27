package com.hackerda.platform.exception;

import com.hackerda.platform.pojo.constant.ErrorCode;

public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String msg;

    public BusinessException(ErrorCode errorCode, String msg){
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
