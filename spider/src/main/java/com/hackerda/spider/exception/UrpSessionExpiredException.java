package com.hackerda.spider.exception;

/**
 * 用户cookie对应的session 过期
 * @author junrong.chen
 */
public class UrpSessionExpiredException extends UrpException{
    public UrpSessionExpiredException(String desc){
        super(desc);
    }
}
