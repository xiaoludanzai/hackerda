package com.hackerda.platform.application;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.domain.user.UserRepository;
import com.hackerda.platform.domain.wechat.WechatAuthService;
import com.hackerda.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Map;

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
    @Lazy
    @Autowired
    private Map<String , WechatPlatform> wechatPlatformMap;


    public WechatStudentUserBO bindByCode(@Nonnull String account, @Nonnull String password, @Nonnull String appId,
                                          @Nonnull String code){
        // 查询对应的openid
        String openId = wechatAuthService.appCodeToOpenId(code);

        return bindByOpenId(account, password, appId, openId);
    }

    public WechatStudentUserBO bindByOpenId(@Nonnull String account, @Nonnull String password, @Nonnull String appId,
                                            @Nonnull String openid) {

        if(studentInfoService.checkCanBind(account, appId, openid)) {
            StudentUserBO studentUserBO = getStudentUserBO(account, password);
            WechatStudentUserBO wechatStudentUserBO = transfer(studentUserBO);
            if(studentUserBO.isUsingDefaultPassword()  && studentInfoService.isCommonWechat(account, appId, openid)) {
                studentRepository.save(wechatStudentUserBO);
                throw new BusinessException(ErrorCode.UNCOMMON_WECHAT, "非常用微信号登录");
            }

            wechatStudentUserBO.bindWechatPlatform(openid, appId, wechatPlatformMap.get(appId));

            studentRepository.save(wechatStudentUserBO);

            return wechatStudentUserBO;

        }else {
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, account + "该学号已经被绑定");
        }
    }

    public WechatStudentUserBO bindCommonWechatUser(@Nonnull StudentAccount account,
                                                    @Nonnull PhoneNumber phoneNumber,
                                                    @Nonnull String appId,
                                                    @Nonnull String openid) {

        if(studentInfoService.checkCanBind(account.getAccount(), appId, openid)) {
            AppStudentUserBO user = userRepository.findByStudentAccount(account);
            if(user.getPhoneNumber().equals(phoneNumber)) {
                WechatStudentUserBO studentUserBO = studentRepository.getWetChatUserByAccount(Integer.parseInt(account.getAccount()));

                studentUserBO.bindWechatPlatform(openid, appId, wechatPlatformMap.get(appId));

                studentRepository.save(studentUserBO);

                return studentUserBO;
            }

            return null;

        }else {
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, account + "该学号已经被绑定");
        }
    }


    public void unbindByPlatform(@Nonnull WechatStudentUserBO wechatStudentUserBO, @Nonnull String appId) {

        wechatStudentUserBO.unbindWechatPlatform(wechatPlatformMap.get(appId));

        studentRepository.save(wechatStudentUserBO);

    }



    private StudentUserBO getStudentUserBO(@Nonnull String account, @Nonnull String password) {
        if(!studentInfoService.checkPasswordValid(account, password)){
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, account + "账号或密码错误");
        }

        StudentUserBO studentUserBO = studentRepository.getByAccount(Integer.parseInt(account));

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

    private WechatStudentUserBO transfer(StudentUserBO studentUser ) {


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
        bo.setSaveOrUpdate(true);

        return bo;
    }
}
