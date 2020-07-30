package com.hackerda.platform.infrastructure.spider;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.spider.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpiderExceptionTransfer {


    BusinessException transfer(Throwable throwable) {
        if (throwable instanceof PasswordUnCorrectException) {
            return new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, "账号或密码错误");
        } else if (throwable instanceof UrpRequestException) {
            return new BusinessException(ErrorCode.LOGIN_ERROR, "无法登录请重试");
        } else if (throwable instanceof UrpSessionExpiredException) {
            return new BusinessException(ErrorCode.URP_EXCEPTION, "session过期请重试");
        } else if (throwable instanceof UrpVerifyCodeException) {
            return new BusinessException(ErrorCode.URP_EXCEPTION, "验证码预测失败 请重试");
        } else if (throwable instanceof UrpTimeoutException) {
            return new BusinessException(ErrorCode.READ_TIMEOUT, "读取超时 请重试");
        } else {
            log.error("spider exception ", throwable);
            return new BusinessException(ErrorCode.SYSTEM_ERROR, throwable.getMessage());
        }

    }
}
