package com.hackerda.platform.application;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.*;
import com.hackerda.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegisterApp {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public void register(AppStudentUserBO appUserBO) {

        if(userInfoService.userCanRegister(appUserBO)) {

            RoleBO role = roleRepository.findByCode(RoleBO.USER);

            appUserBO.grantRole(role);

            userRepository.store(appUserBO);

        } else {
            throw new BusinessException(ErrorCode.USER_ACCOUNT_EXIST, "用户手机号或者用户名已经被注册");
        }
    }


    public AppUserBO getUserByStudentAccount(StudentAccount studentAccount) {

        AppStudentUserBO appStudentUserBO = userRepository.findByStudentAccount(studentAccount);

        if(appStudentUserBO != null) {
            List<RoleBO> roleBOList = roleRepository.findByUserName(appStudentUserBO.getUserName());

            appStudentUserBO.grantRole(roleBOList);
        }

        return appStudentUserBO;
    }
}
