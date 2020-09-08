package com.hackerda.platform.infrastructure.user;

import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.LifeCycleStatus;
import com.hackerda.platform.domain.user.UserInfoService;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserDao userDao;


    @Override
    public boolean userCanRegister(AppStudentUserBO appStudentUserBO) {

        User user = userDao.selectByStudentAccount(appStudentUserBO.getAccount().getAccount());
        if(user == null) {
            return true;
        }
        else if(user.getLifeCycleStatus() == LifeCycleStatus.Logout.getCode()) {
            return false;
        }

        return userDao.selectByMobile(appStudentUserBO.getPhoneNumber().getEnableNumber()) == null;

    }

    @Override
    public boolean needToCheckCommonWechat(AppStudentUserBO appStudentUserBO) {
        return false;
    }
}
