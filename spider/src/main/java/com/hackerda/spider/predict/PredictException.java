package com.hackerda.spider.predict;

import lombok.Data;

/**
 * 验证码预测异常
 *
 * @author JR Chan
 */
@Data
public class PredictException extends RuntimeException {

    private int statusCode;
    private String message;

    public PredictException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
