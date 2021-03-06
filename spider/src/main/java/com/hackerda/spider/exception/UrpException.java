package com.hackerda.spider.exception;

/**
 * 所有的校务网异常
 * @author junrong.chen
 */
public class UrpException extends RuntimeException {
    public UrpException(){
        super();
    }

    public UrpException(String desc){
        super(desc);
    }

    public UrpException(String description, Throwable e) {
        super(description, e);
    }
}
