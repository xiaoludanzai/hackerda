package com.hackerda.platform.repository;

import com.hackerda.platform.pojo.constant.ErrorCode;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpEvaluationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class FetchExceptionHandler {

    private final static ExceptionMsg ACCOUNT_OR_PASSWORD_INVALID =
            new ExceptionMsg(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号或密码错误");

    private final static ExceptionMsg EVALUATION_ERROR =
            new ExceptionMsg(ErrorCode.Evaluation_ERROR.getErrorCode(), "评估未完成，无法查看成绩");

    private final static ExceptionMsg READ_TIMEOUT =
            new ExceptionMsg(ErrorCode.READ_TIMEOUT.getErrorCode(), "抓取超时");

    public ExceptionMsg handle(Throwable exception){

        if(exception instanceof ExecutionException) {
            exception = exception.getCause();
        }

        if (exception instanceof PasswordUnCorrectException) {
            return ACCOUNT_OR_PASSWORD_INVALID;
        }
        else if (exception instanceof UrpEvaluationException) {
            return EVALUATION_ERROR;
        }
        else if(exception instanceof TimeoutException) {
            return READ_TIMEOUT;
        }else {
            log.error("spider fetch error", exception);
            return new ExceptionMsg(ErrorCode.SYSTEM_ERROR.getErrorCode(), exception.getMessage());
        }
    }
}
