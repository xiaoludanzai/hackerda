package com.hackerda.platform.domain.student;

public interface StudentInfoService {

    boolean checkPasswordValid(String account, String enablePassword);

    boolean checkCanBind(String account, String appId, String openid);

    WechatStudentUserBO getStudentInfo(String account, String enablePassword);

    boolean isCommonWechat(String account, String appId, String openid);
}
