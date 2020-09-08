package com.hackerda.platform.infrastructure.repository.user;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.*;
import com.hackerda.platform.infrastructure.database.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class UserAdapter {


    public User toDO(AppUserBO appUserBO) {

        User user = new User();

        // 密码相关
        user.setPassword(appUserBO.getPassword());
        user.setSalt(appUserBO.getSalt());
        user.setUseDefaultPassword(appUserBO.isUseDefaultPassword()? (byte) 1 : (byte) 0);

        // 展示相关
        user.setAvatarPath(appUserBO.getAvatarPath());
        user.setGender(appUserBO.getGender().getCode());
        user.setNickName(appUserBO.getNickname());
        user.setIntroduction(appUserBO.getIntroduction());

        // 用户区分相关
        user.setUserName(appUserBO.getUserName());
        user.setMobile(appUserBO.getPhoneNumber().getEnableNumber());

        user.setUserType(appUserBO.getUserType().getCode());

        user.setLifeCycleStatus(appUserBO.getLifeCycleStatus().getCode());

        return user;
    }

    @Nullable
    public AppStudentUserBO toStudentBO(User user, StudentAccount studentAccount) {
        if(user == null) {
            return null;
        }

        PhoneNumber phoneNumber = new PhoneNumber(user.getMobile());

        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, user.getUserName(), user.getNickName(),
                user.getPassword(),
                user.getSalt(),
                user.getAvatarPath(),
                phoneNumber, Gender.formCode(user.getGender()),
                user.getIntroduction(), user.getUseDefaultPassword() == (byte) 1);

        appStudentUserBO.setUserType(UserType.Student);
        appStudentUserBO.setLifeCycleStatus(LifeCycleStatus.getByCode(user.getLifeCycleStatus()));

        return appStudentUserBO;
    }

    @Nullable
    public AppUserBO toBO(User user) {
        if(user == null) {
            return null;
        }

        PhoneNumber phoneNumber = new PhoneNumber(user.getMobile());

        AppUserBO appUserBO = new AppUserBO(user.getUserName(), user.getNickName(),
                user.getPassword(),
                user.getSalt(),
                user.getAvatarPath(),
                phoneNumber, Gender.formCode(user.getGender()),
                user.getIntroduction(), user.getUseDefaultPassword() == (byte) 1);

        appUserBO.setUserType(UserType.getByCode(user.getUserType()));
        appUserBO.setLifeCycleStatus(LifeCycleStatus.getByCode(user.getLifeCycleStatus()));

        return appUserBO;
    }
}
