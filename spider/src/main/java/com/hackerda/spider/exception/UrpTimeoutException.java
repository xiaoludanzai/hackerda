package com.hackerda.spider.exception;

import java.io.IOException;

/**
 * @author junrong.chen
 * @date 2019/7/17
 */
public class UrpTimeoutException extends UrpException {
    public UrpTimeoutException(String description, IOException e) {
        super(description, e);
    }

    public UrpTimeoutException(String description) {
        super(description);
    }
}
