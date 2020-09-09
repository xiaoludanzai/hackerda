package com.hackerda.platform.domain.constant;

/**
 * @author junrong.chen
 * @date 2018/10/10
 */
public enum  ErrorCode {
	/**
	 * 客户端错误
	 */
	NO_DATA(400),
	USER_UNAUTHORIZED(401),
	ACCOUNT_OR_PASSWORD_INVALID(402),
	OPENID_EXIST(403),
	VERIFY_CODE_ERROR(404),
	/**
	 * 无法登录，通常是输入密码次数过多
	 */
	LOGIN_ERROR(405),

	/**
	 * 未完成评估，需要手动到教务网上完成评估
	 */
	Evaluation_ERROR(406),

	/**
	 * 教务网的异常，通常网络波动等原因，提醒用户重试即可
	 */
	URP_EXCEPTION(407),

	/**
	 * 账号已经被绑定
	 */
	ACCOUNT_HAS_BIND(408),

	/**
	 * 用户信息不存在
	 */
	ACCOUNT_MISS(409),

	/**
	 * 用户手机号或者用户名已经被注册
	 */
	USER_ACCOUNT_EXIST(410),

	/**
	 * 请求参数校验不通过
	 */
	DATA_NOT_VALID(411),

	/**
	 * 使用非常用微信登录，需要调用验证身份
	 */
	UNCOMMON_WECHAT (412),

	/**
	 * 该微信账号已经被使用
	 */
	WECHAT_HAS_USED(413),

	/**
	 *服务器端错误
	 */
	SYSTEM_ERROR(500),
	READ_TIMEOUT(504);

	private int errorCode;

	ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
