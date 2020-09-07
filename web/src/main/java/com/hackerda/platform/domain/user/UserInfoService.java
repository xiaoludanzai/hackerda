package com.hackerda.platform.domain.user;

public interface UserInfoService {

    boolean userCanRegister(AppStudentUserBO appStudentUserBO);

    boolean needToCheckCommonWechat(AppStudentUserBO appStudentUserBO);
}
