package com.hackerda.platform.application;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.user.*;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserRegisterApp {

    @Autowired
    private UserRegisterAssist userRegisterAssist;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRegisterRecordRepository userRegisterRecordRepository;


    @Transactional
    public void register(AppStudentUserBO appUserBO, WechatUser wechatUser) {

        WechatStudentUserBO student = studentRepository.findWetChatUser(appUserBO.getAccount());

        if (student == null) {
            throw new BusinessException(ErrorCode.ACCOUNT_MISS, "学生信息不存在");
        }
        if(!student.hasBindWechatUser(wechatUser)) {
            throw new BusinessException(ErrorCode.USER_UNAUTHORIZED, "注册用户的学号与当前微信未绑定");
        }
        if (!student.getIsCorrect()) {
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, "教务网密码错误");
        }



        if (userRegisterAssist.wechatHasRegister(wechatUser)) {
            throw new BusinessException(ErrorCode.WECHAT_HAS_USED, "该微信号已经被注册");
        }

        if (userRegisterAssist.userHasRegister(appUserBO)) {
            throw new BusinessException(ErrorCode.USER_ACCOUNT_EXIST, "用户手机号或者学号已经被注册");
        }

        RoleBO role = roleRepository.findByCode(RoleBO.USER);

        appUserBO.grantRole(role);
        appUserBO.setLifeCycleStatus(LifeCycleStatus.Normal);
        userRepository.store(appUserBO);

        // 插入一条注册成功记录
        UserRegisterRecordBO record = new UserRegisterRecordBO(appUserBO.getUserName(), wechatUser,
                appUserBO.getPhoneNumber(), appUserBO.getLifeCycleStatus());
        userRegisterRecordRepository.save(record);
    }


    public AppStudentUserBO getUserByStudentAccount(StudentAccount studentAccount) {

        AppStudentUserBO appStudentUserBO = userRepository.findByStudentAccount(studentAccount);

        if (appStudentUserBO != null && appStudentUserBO.isNormalStatus()) {
            List<RoleBO> roleBOList = roleRepository.findByUserName(appStudentUserBO.getUserName());
            appStudentUserBO.grantRole(roleBOList);
        }

        return appStudentUserBO;
    }

    @Transactional
    public void logout(String operator, AppStudentUserBO appUserBO, LogoutType logoutType, String logoutReason) {
        List<UserRegisterRecordBO> registerRecordList = userRegisterRecordRepository.findByUserName(appUserBO.getUserName());

        if(registerRecordList.isEmpty() || registerRecordList.size() % 2 != 0) {
            appUserBO.logout();
            userRepository.update(appUserBO);
            UserRegisterRecordBO record = registerRecordList.get(registerRecordList.size() - 1);
            record.setLifeCycleStatus(LifeCycleStatus.Logout);

            userRegisterRecordRepository.save(record);

            LogoutRecordBO logoutRecordBO = new LogoutRecordBO(record.getId(), logoutType, logoutReason, operator);

            userRegisterRecordRepository.save(logoutRecordBO);

        } else {
            log.error("user {} can`t find register record", operator);
            throw new RuntimeException("该用户无法注销");
        }
    }
}
