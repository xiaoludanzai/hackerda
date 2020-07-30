package com.hackerda.platform.service.rbac;

import com.hackerda.platform.controller.vo.StudentUserDetailVO;

import javax.annotation.Nonnull;

/**
 * 用户授权
 * @author JR Chan
 */
public interface UserAuthorizeService {
    /**
     * 对于教务网登陆的用户，执行登陆流程。登陆成功以后对用户授权并生成token
     * token是以后调用对应接口的凭据
     * @param account 教务网学号
     * @param password 教务网密码
     * @param appId 微信对应平台的appId
     * @param openid appID 下对应的用户openid
     * @return 对应学生用户的详细信息
     */
    StudentUserDetailVO studentAuthorize(@Nonnull String account, @Nonnull String password, @Nonnull String appId, @Nonnull String openid);

    StudentUserDetailVO appStudentAuthorize(@Nonnull String account, @Nonnull String password, @Nonnull String appId,
                                            @Nonnull String code);

    void appStudentRevokeAuthorize(@Nonnull String account, @Nonnull String appId);
}
