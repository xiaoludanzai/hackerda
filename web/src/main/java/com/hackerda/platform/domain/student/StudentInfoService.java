package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.wechat.WechatUser;

public interface StudentInfoService {

    /**
     * 检查用户教务网的账号密码是否正确
     * 每次登录都会实时请求教务网验证密码是否正确
     * 如果教务网请求超时（通常为服务不可用），那么会查找数据库中的记录进行比对。
     *
     * @param account 教务网账号
     * @param enablePassword 教务网账号对应的明文密码
     * @return 密码是否正确
     */
    boolean checkPasswordValid(String account, String enablePassword);

    /**
     * 学号是否可以绑定
     *
     * 对于非白名单学号，一个学号只能被一个openid绑定。
     * 对于白名单学号，一个学号可以与多个openid绑定。所以如果学号在白名单内，会一直返回true。
     *
     * @param account 教务网账号
     * @param wechatUser 微信用户
     * @return 可以绑定则返回true
     */
    boolean checkCanBind(StudentAccount account, WechatUser wechatUser);

    WechatStudentUserBO getStudentInfo(StudentAccount account, String enablePassword);

    boolean isCommonWechat(StudentAccount account, WechatUser wechatUser);

}
