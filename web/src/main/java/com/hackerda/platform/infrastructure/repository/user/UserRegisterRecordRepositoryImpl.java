package com.hackerda.platform.infrastructure.repository.user;

import com.hackerda.platform.domain.user.*;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.infrastructure.database.mapper.UserLogoutRecordMapper;
import com.hackerda.platform.infrastructure.database.mapper.UserRegisterRecordMapper;
import com.hackerda.platform.infrastructure.database.model.UserLogoutRecord;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecord;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterRecordRepositoryImpl implements UserRegisterRecordRepository {

    @Autowired
    private UserRegisterRecordMapper userRegisterRecordMapper;
    @Autowired
    private UserLogoutRecordMapper userLogoutRecordMapper;

    @Override
    public void save(UserRegisterRecordBO userRegisterRecordBO) {

        UserRegisterRecord record = adapterDO(userRegisterRecordBO);

        userRegisterRecordMapper.insertSelective(record);

        userRegisterRecordBO.setId(record.getId());

    }

    @Override
    public UserRegisterRecordBO findByUserName(String userName) {
        UserRegisterRecordExample example = new UserRegisterRecordExample();
        example.createCriteria().andUserNameEqualTo(userName);

        UserRegisterRecord record = userRegisterRecordMapper.selectByExample(example).stream().findFirst().orElse(null);
        if(record == null) {
            return null;
        }

        WechatUser wechatUser = new WechatUser(record.getAppid(), record.getOpenid());
        PhoneNumber phoneNumber = new PhoneNumber(record.getPhoneNumber());
        LifeCycleStatus lifeCycleStatus = LifeCycleStatus.getByCode(record.getLifeCycleStatus());

        UserRegisterRecordBO bo = new UserRegisterRecordBO(record.getUserName(),  wechatUser, phoneNumber, lifeCycleStatus);
        bo.setId(record.getId());

        return bo;
    }

    @Override
    public void update(UserRegisterRecordBO userRegisterRecordBO) {

        UserRegisterRecord record = adapterDO(userRegisterRecordBO);
        record.setId(userRegisterRecordBO.getId());

        userRegisterRecordMapper.updateByPrimaryKeySelective(record);

    }

    @Override
    public void save(LogoutRecordBO logoutRecordBO) {

        UserLogoutRecord logoutRecord = new UserLogoutRecord();
        logoutRecord.setLogoutReason(logoutRecordBO.getLogoutReason());
        logoutRecord.setLogoutRecordId(logoutRecordBO.getLogoutRecordId());
        logoutRecord.setLogoutType(logoutRecordBO.getLogoutType().getCode());
        logoutRecord.setOperator(logoutRecordBO.getOperator());

        userLogoutRecordMapper.insertSelective(logoutRecord);
    }

    private UserRegisterRecord adapterDO(UserRegisterRecordBO userRegisterRecordBO) {

        UserRegisterRecord record = new UserRegisterRecord();
        record.setAppid(userRegisterRecordBO.getWechatUser().getAppId());
        record.setOpenid(userRegisterRecordBO.getWechatUser().getOpenId());
        record.setPhoneNumber(userRegisterRecordBO.getPhoneNumber().getEnableNumber());
        record.setUserName(userRegisterRecordBO.getUserName());
        record.setLifeCycleStatus(userRegisterRecordBO.getLifeCycleStatus().getCode());

        return record;
    }
}
