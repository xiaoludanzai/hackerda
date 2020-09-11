package com.hackerda.platform.application;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.domain.user.UserRepository;
import com.hackerda.platform.domain.wechat.ActionRecord;
import com.hackerda.platform.domain.wechat.WechatActionRecordRepository;
import com.hackerda.platform.domain.wechat.WechatAuthService;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private StudentInfoAssist studentInfoAssist;
    @Autowired
    private WechatActionRecordRepository wechatActionRecordRepository;


    public WechatStudentUserBO bindByCode(@Nonnull StudentAccount studentAccount, @Nonnull String password, @Nonnull String appId,
                                          @Nonnull String code){
        // 查询对应的openid
        String openId = wechatAuthService.appCodeToOpenId(code);

        return bindByOpenId(studentAccount, password, new WechatUser(appId, openId));
    }

    public WechatStudentUserBO bindByOpenId(@Nonnull StudentAccount account, @Nonnull String password, WechatUser wechatUser) {
        WechatStudentUserBO wechatStudentUserBO = getStudentUserBO(account, password, wechatUser);

        if(!wechatStudentUserBO.hasBindApp(wechatUser.getAppId())) {

            if(studentInfoAssist.needToCheckWechatCommentUser()
                    && wechatStudentUserBO.isUsingDefaultPassword() && !studentInfoService.isCommonWechat(account, wechatUser)) {
                studentRepository.save(wechatStudentUserBO);
                wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.UnCommonWechat, account));
                throw new BusinessException(ErrorCode.UNCOMMON_WECHAT, "非常用微信号登录");
            }

            wechatStudentUserBO.bindWechatUser(wechatUser);

            studentRepository.save(wechatStudentUserBO);
            wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.Login, account));
            return wechatStudentUserBO;

        }else if(wechatStudentUserBO.hasBindWechatUser(wechatUser)){
            return wechatStudentUserBO;
        } else {
            wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.AccountHasBind, account));
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, account + "该学号已经被绑定");
        }
    }


    public WechatStudentUserBO bindCommonWechatUser(@Nonnull StudentAccount account,
                                                    @Nonnull PhoneNumber phoneNumber,
                                                    WechatUser wechatUser) {

        if(studentInfoService.checkCanBind(account, wechatUser)) {
            AppStudentUserBO user = userRepository.findByStudentAccount(account);

            if(user == null) {
                throw new BusinessException(ErrorCode.ACCOUNT_MISS, "用户信息不存在");
            }
            if(user.getPhoneNumber().equals(phoneNumber)) {
                StudentUserBO studentUserBO = studentRepository.find(account);
                WechatStudentUserBO wechatStudentUserBO = transfer(studentUserBO);
                wechatStudentUserBO.bindWechatUser(wechatUser);

                studentRepository.save(wechatStudentUserBO);

                return wechatStudentUserBO;
            }

            throw new BusinessException(ErrorCode.UNCOMMON_WECHAT, "非常用微信号登录");

        }else {
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, account + "该学号已经被绑定");
        }
    }


    public void unbindByPlatform(@Nonnull WechatStudentUserBO wechatStudentUserBO, @Nonnull String appId) {

        WechatUser wechatUser = wechatStudentUserBO.revokeWechatUser(appId);

        studentRepository.save(wechatStudentUserBO);

        wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.AccountHasBind, wechatStudentUserBO.getAccount()));
    }

    private WechatStudentUserBO getStudentUserBO(@Nonnull StudentAccount account, @Nonnull String password,
                                                 WechatUser wechatUser) {
        if(!studentInfoService.checkPasswordValid(account.getAccount(), password)){
            wechatActionRecordRepository.save(new ActionRecord(wechatUser, Action.Login, account));
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

    private WechatStudentUserBO transfer(StudentUserBO studentUser) {

        WechatStudentUserBO bo = new WechatStudentUserBO();

        bo.setAcademyName(studentUser.getAcademyName());
        bo.setAccount(studentUser.getAccount());
        bo.setClassName(studentUser.getClassName());
        bo.setEthnic(studentUser.getEthnic());
        bo.setIsCorrect(studentUser.getIsCorrect());

        bo.setSex(studentUser.getSex());
        bo.setSubjectName(studentUser.getSubjectName());
        bo.setUrpClassNum(studentUser.getUrpClassNum());
        bo.setPassword(studentUser.getPassword());
        bo.setName(studentUser.getName());

        bo.setKey(studentUser.getKey());
        bo.setSaveOrUpdate(studentUser.isSaveOrUpdate());

        return bo;
    }
}
