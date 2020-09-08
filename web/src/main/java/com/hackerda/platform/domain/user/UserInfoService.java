package com.hackerda.platform.domain.user;

public interface UserInfoService {

    /**
     * 检查该小程序学生用户能否注册
     * 校验的事项
     * 1.该用户的学号是否已经注册了科大圈用户身份
     * 2.改用户的手机号已经被其它账户注册
     *
     * @param appStudentUserBO 小程序学生用户
     * @return 如果可以注册则返回ture
     */
    boolean userCanRegister(AppStudentUserBO appStudentUserBO);

    boolean needToCheckCommonWechat(AppStudentUserBO appStudentUserBO);
}
