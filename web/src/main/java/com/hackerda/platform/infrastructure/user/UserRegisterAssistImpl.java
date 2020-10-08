package com.hackerda.platform.infrastructure.user;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.LifeCycleStatus;
import com.hackerda.platform.domain.user.UserRegisterAssist;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.mapper.UserRegisterRecordMapper;
import com.hackerda.platform.infrastructure.database.model.User;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecord;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserRegisterAssistImpl implements UserRegisterAssist {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRegisterRecordMapper userRegisterRecordMapper;
    @Value("${user.register.checkWechat:true}")
    private boolean checkWechat;

    @Override
    public boolean userHasRegister(AppStudentUserBO appStudentUserBO) {

        if(studentHasRegister(appStudentUserBO.getAccount())) {
            return true;
        }

        return userDao.selectByMobile(appStudentUserBO.getPhoneNumber().getEnableNumber()) != null;

    }

    @Override
    public boolean studentHasRegister(StudentAccount studentAccount) {

        User user = userDao.selectByStudentAccount(studentAccount.getAccount());
        return user != null;
    }


    @Override
    public boolean wechatHasRegister(WechatUser wechatUser) {

        UserRegisterRecordExample example = new UserRegisterRecordExample();
        example.createCriteria().andAppidEqualTo(wechatUser.getAppId())
                .andOpenidEqualTo(wechatUser.getOpenId());

        List<UserRegisterRecord> recordList = userRegisterRecordMapper.selectByExample(example);

        return recordList.size() % 2 != 0;
    }

    @Override
    public boolean wechatNeedToCheck(WechatUser wechatUser) {
        return checkWechat;
    }


}
