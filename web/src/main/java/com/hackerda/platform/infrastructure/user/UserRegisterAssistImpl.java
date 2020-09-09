package com.hackerda.platform.infrastructure.user;

import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.LifeCycleStatus;
import com.hackerda.platform.domain.user.UserRegisterAssist;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.mapper.UserRegisterRecordMapper;
import com.hackerda.platform.infrastructure.database.model.User;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRegisterAssistImpl implements UserRegisterAssist {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRegisterRecordMapper userRegisterRecordMapper;


    @Override
    public boolean userCanRegister(AppStudentUserBO appStudentUserBO) {

        User user = userDao.selectByStudentAccount(appStudentUserBO.getAccount().getAccount());
        if(user == null) {
            return true;
        }

        return userDao.selectByMobile(appStudentUserBO.getPhoneNumber().getEnableNumber()) == null;

    }

    @Override
    public boolean wechatCanRegister(WechatUser wechatUser) {

        UserRegisterRecordExample example = new UserRegisterRecordExample();
        example.createCriteria().andAppidEqualTo(wechatUser.getAppId())
                .andOpenidEqualTo(wechatUser.getOpenId());
        return userRegisterRecordMapper.selectByExample(example).size() % 2 == 0;
    }


}
