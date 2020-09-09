package com.hackerda.platform.application;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.user.*;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        StudentUserBO student = studentRepository.getByAccount(appUserBO.getAccount());

        if (student == null) {
            throw new BusinessException(ErrorCode.ACCOUNT_MISS, "学生信息不存在");
        }
        if (!student.getIsCorrect()) {
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, "教务网密码错误");
        }

        if (!userRegisterAssist.wechatCanRegister(wechatUser)) {
            throw new BusinessException(ErrorCode.WECHAT_HAS_USED, "该微信号不能注册");
        }

        if (!userRegisterAssist.userCanRegister(appUserBO)) {
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


    public AppUserBO getUserByStudentAccount(StudentAccount studentAccount) {

        AppStudentUserBO appStudentUserBO = userRepository.findByStudentAccount(studentAccount);

        if (appStudentUserBO != null && appStudentUserBO.isNormalStatus()) {
            List<RoleBO> roleBOList = roleRepository.findByUserName(appStudentUserBO.getUserName());
            appStudentUserBO.grantRole(roleBOList);
        }

        return appStudentUserBO;
    }


    public void logout(AppStudentUserBO appUserBO, LogoutType logoutType) {
        UserRegisterRecordBO record = userRegisterRecordRepository.findByUserName(appUserBO.getUserName());

        if(record != null) {
            appUserBO.logout();
            userRepository.update(appUserBO);

            record.setLifeCycleStatus(LifeCycleStatus.Logout);

            userRegisterRecordRepository.save(record);
        }

    }
}
