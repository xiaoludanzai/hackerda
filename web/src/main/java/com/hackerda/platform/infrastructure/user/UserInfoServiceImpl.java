package com.hackerda.platform.infrastructure.user;

import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.UserInfoService;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserDao userDao;


    @Override
    public boolean userCanRegister(AppStudentUserBO appStudentUserBO) {

        return userDao.selectByMobile(appStudentUserBO.getPhoneNumber().getEnableNumber()) == null;

    }
}
