package com.hackerda.platform.service;

import com.hackerda.platform.application.UserRegisterApp;
import com.hackerda.platform.controller.request.CreateUserByStudentRequest;
import com.hackerda.platform.controller.vo.AppUserVO;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.AppUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class UserService {

    @Autowired
    private UserRegisterApp userRegisterApp;

    public AppUserVO registerByStudent (CreateUserByStudentRequest request) {

        StudentAccount studentAccount = new StudentAccount(request.getStudentAccount());

        Gender gender = Gender.formCode(request.getGender());

        PhoneNumber phoneNumber = new PhoneNumber(request.getPhoneNumber());

        AppStudentUserBO appUserBO = new AppStudentUserBO(studentAccount, request.getNickName(), "1", request.getAvatarUrl(),
                phoneNumber, gender, request.getSignature());

        appUserBO.setUseDefaultPassword(true);


        userRegisterApp.register(appUserBO);

        return toVO(appUserBO);


    }


    public AppUserVO getUserByStudentAccount (String account) {

        StudentAccount studentAccount = new StudentAccount(account);


        AppUserBO user = userRegisterApp.getUserByStudentAccount(studentAccount);

        return toVO(user);


    }

    @Nullable
    private AppUserVO toVO(AppUserBO appUserBO) {

        if(appUserBO == null) {
            return null;
        }

        AppUserVO appUserVO = new AppUserVO();

        appUserVO.setAvatarPath(appUserBO.getAvatarPath());
        appUserVO.setGender(appUserBO.getGender().getDesc());
        appUserVO.setMobile(appUserBO.getPhoneNumber().getBlurNUmber());
        appUserVO.setIntroduction(appUserBO.getIntroduction());
        appUserVO.setUseDefaultPassword(appUserBO.isUseDefaultPassword());
        appUserVO.setNickname(appUserBO.getNickname());

        String token = JwtUtils.signForUserDetail(appUserBO.getUserName(), new String[0], new String[0], appUserBO.getSalt());

        appUserVO.setToken(token);

        return appUserVO;
    }

}
