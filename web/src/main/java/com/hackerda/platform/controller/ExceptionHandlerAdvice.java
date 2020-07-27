package com.hackerda.platform.controller;

import com.hackerda.platform.pojo.WebResponse;
import com.hackerda.platform.pojo.constant.ErrorCode;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpEvaluationException;
import com.hackerda.spider.exception.UrpException;
import com.hackerda.spider.exception.UrpRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author junrong.chen
 * @date 2018/10/10
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {


	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public WebResponse handle401() {
		return WebResponse.failUnauthorized("用户未授权");
	}



	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthorizationException.class)
	public WebResponse handle403() {
		return WebResponse.failWithForbidden("没有访问权限");
	}

	@ExceptionHandler(value = Exception.class)
	public WebResponse handleException(Exception e) {
		if (e instanceof PasswordUnCorrectException) {
			return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), e.getMessage());
		}else if(e instanceof UrpRequestException) {
			return WebResponse.fail(ErrorCode.LOGIN_ERROR.getErrorCode(), "can`t verify user");
		}else if(e instanceof UrpEvaluationException) {
			return WebResponse.fail(ErrorCode.Evaluation_ERROR.getErrorCode(), "未完成评估");
		}else if(e instanceof UrpException){
			return WebResponse.fail(ErrorCode.URP_EXCEPTION.getErrorCode(), "教务网异常，请重试");
		}
		log.error("request fail", e);
		return WebResponse.fail(ErrorCode.SYSTEM_ERROR.getErrorCode(), "服务器出了点小问题");
	}
}
