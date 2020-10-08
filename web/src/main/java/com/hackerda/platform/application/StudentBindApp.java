package com.hackerda.platform.application;

import com.google.common.annotations.VisibleForTesting;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.domain.user.UserRegisterAssist;
import com.hackerda.platform.domain.user.UserRepository;
import com.hackerda.platform.domain.wechat.ActionRecord;
import com.hackerda.platform.domain.wechat.WechatActionRecordRepository;
import com.hackerda.platform.domain.wechat.WechatAuthService;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class StudentBindApp {

    @Autowired
    private WechatAuthService wechatAuthService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void setStudentInfoAssist(StudentInfoAssist studentInfoAssist) {
        this.studentInfoAssist = studentInfoAssist;
    }

    private StudentInfoAssist studentInfoAssist;


    @Autowired
    private WechatActionRecordRepository wechatActionRecordRepository;
    @Autowired
    private UserRegisterAssist userRegisterAssist;


    public WechatStudentUserBO bindByCode(@Nonnull StudentAccount studentAccount, @Nonnull String password, @Nonnull String appId,
                                          @Nonnull String code){
        // 查询对应的openid
        String openId = wechatAuthService.appCodeToOpenId(code);

        return bindByOpenId(studentAccount, password, new WechatUser(appId, openId));
    }

    public WechatStudentUserBO bindByOpenId(@Nonnull StudentAccount account, @Nonnull String password, WechatUser wechatUser) {

        try {
            WechatStudentUserBO wechatStudentUserBO = getStudentUserBO(account, password);
            WechatStudentUserBO userBO = bindByOpenId(wechatStudentUserBO, wechatUser);
            wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.Login, wechatStudentUserBO.getAccount()));
            return userBO;
        } catch (BusinessException e) {
            Action action = null;
            if(e.getErrorCode() == ErrorCode.UNCOMMON_WECHAT) {
                action = Action.UnCommonWechat;
            } else if(e.getErrorCode() == ErrorCode.ACCOUNT_OR_PASSWORD_INVALID){
                action = Action.PasswordUnCorrect;
            }else if(e.getErrorCode() == ErrorCode.ACCOUNT_HAS_BIND) {
                action = Action.AccountHasBind;
            }
            if(action != null) {
                wechatActionRecordRepository.save(new ActionRecord(wechatUser, action, account));
            }

            throw e;
        }

    }

    @VisibleForTesting
    WechatStudentUserBO bindByOpenId(WechatStudentUserBO wechatStudentUserBO, WechatUser wechatUser) {
        if(!wechatStudentUserBO.hasBindApp(wechatUser.getAppId())) {
            if(needToCheckCommonWechat(wechatStudentUserBO) && !studentInfoAssist.isCommonWechat(wechatStudentUserBO.getAccount(),
                    wechatUser)) {
                studentRepository.save(wechatStudentUserBO);
                throw new BusinessException(ErrorCode.UNCOMMON_WECHAT, "非常用微信号登录");
            }

            wechatStudentUserBO.bindWechatUser(wechatUser);

            studentRepository.save(wechatStudentUserBO);

            return wechatStudentUserBO;
        }else if(wechatStudentUserBO.hasBindWechatUser(wechatUser)){
            return wechatStudentUserBO;
        } else {
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, wechatStudentUserBO.getAccount() + "该学号已经被绑定");
        }

    }

    private boolean needToCheckCommonWechat(WechatStudentUserBO wechatStudentUserBO) {
        return studentInfoAssist.needToCheckWechatCommentUser() &&
                userRegisterAssist.studentHasRegister(wechatStudentUserBO.getAccount()) &&
                wechatStudentUserBO.isUsingDefaultPassword();
    }

    public WechatStudentUserBO bindCommonWechatUser(@Nonnull StudentAccount account,
                                                    @Nonnull PhoneNumber phoneNumber,
                                                    WechatUser wechatUser) {
        WechatStudentUserBO studentUserBO = studentRepository.findWetChatUser(account);
        if(!studentUserBO.hasBindApp(wechatUser.getAppId())) {
            AppStudentUserBO user = userRepository.findByStudentAccount(account);

            if(user == null) {
                throw new BusinessException(ErrorCode.ACCOUNT_MISS, "用户信息不存在");
            }
            if(user.getPhoneNumber().equals(phoneNumber)) {

                studentUserBO.bindWechatUser(wechatUser);

                studentRepository.save(studentUserBO);
                wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.AuthLogin, account));
                return studentUserBO;
            }

            throw new BusinessException(ErrorCode.UNCOMMON_WECHAT, "非常用微信号登录");

        }else {
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, account + "该学号已经被绑定");
        }
    }


    public void unbindByPlatform(@Nonnull WechatStudentUserBO wechatStudentUserBO, @Nonnull String appId) {

        wechatStudentUserBO.revokeWechatUser(appId);

        studentRepository.save(wechatStudentUserBO);

    }

    @VisibleForTesting
    WechatStudentUserBO getStudentUserBO(@Nonnull StudentAccount account, @Nonnull String password) {
        if(!studentInfoService.checkPasswordValid(account.getAccount(), password)){
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, account + "账号或密码错误");
        }

        WechatStudentUserBO studentUserBO = studentRepository.findWetChatUser(account);

        if(studentUserBO != null && !studentUserBO.checkEnablePasswordIsCorrect(password)) {
            studentUserBO.updatePassword(password);
        }if(studentUserBO == null) {
            studentUserBO = studentInfoService.getStudentInfo(account, password);
        }

        if(studentUserBO == null){
            throw new BusinessException(ErrorCode.ACCOUNT_MISS, account + " 无法获取学号信息");
        }

        return studentUserBO;
    }
}
