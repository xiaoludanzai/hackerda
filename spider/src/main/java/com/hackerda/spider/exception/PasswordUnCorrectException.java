package com.hackerda.spider.exception;

public class PasswordUnCorrectException extends RuntimeException {
    public PasswordUnCorrectException() {
        super();
    }

    public PasswordUnCorrectException(String description) {
        super(description);
    }

}
