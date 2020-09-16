package com.hackerda.platform.aggregator;

import com.hackerda.platform.controller.vo.AppUserVO;
import com.hackerda.platform.controller.vo.StudentUserDetailVO;
import com.hackerda.platform.controller.vo.UserInfoVO;
import com.hackerda.platform.service.UserService;
import com.hackerda.platform.service.rbac.UserAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class UserInfoAggregator {

    @Autowired
    private UserAuthorizeService userAuthorizeService;
    @Autowired
    private UserService userService;

    public UserInfoVO studentAuthorize(@Nonnull String account, @Nonnull String password, @Nonnull String appId,
                                @Nonnull String openid) {

        StudentUserDetailVO detailVO = userAuthorizeService.studentAuthorize(account, password, appId, openid);

        AppUserVO appUserVO = userService.getUserByStudentAccount(detailVO.getAccount().toString());


        UserInfoVO infoVO = new UserInfoVO();

        infoVO.setStudentInfo(detailVO);
        infoVO.setUserInfo(appUserVO);

        return infoVO;
    }


    public UserInfoVO bindCommonWechatUser(@Nonnull String account, @Nonnull String phoneNumber,
                                             @Nonnull String appId, @Nonnull String openId) {

        StudentUserDetailVO detailVO = userAuthorizeService.bindCommonWechatUser(account, phoneNumber, appId, openId);

        AppUserVO appUserVO = userService.getUserByStudentAccount(detailVO.getAccount().toString());


        UserInfoVO infoVO = new UserInfoVO();

        infoVO.setStudentInfo(detailVO);
        infoVO.setUserInfo(appUserVO);

        return infoVO;

    }
}
