package com.hackerda.platform.application;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentInfoService;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
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
    private StudentUserRepository studentUserRepository;
    @Autowired
    private StudentInfoService studentInfoService;
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
            WechatStudentUserBO wechatStudentUserBO = getStudentUserBO(account, password);

            wechatStudentUserBO.bindWechatPlatform(openid, appId, wechatPlatformMap.get(appId));

            studentUserRepository.save(wechatStudentUserBO);

            return wechatStudentUserBO;

        }else {
            throw new BusinessException(ErrorCode.ACCOUNT_HAS_BIND, account + "该学号已经被绑定");
        }
    }


    public void unbindByPlatform(@Nonnull WechatStudentUserBO wechatStudentUserBO, @Nonnull String appId) {

        wechatStudentUserBO.unbindWechatPlatform(wechatPlatformMap.get(appId));

        studentUserRepository.save(wechatStudentUserBO);

    }



    private WechatStudentUserBO getStudentUserBO(@Nonnull String account, @Nonnull String password) {
        if(!studentInfoService.checkPasswordValid(account, password)){
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, account + "账号或密码错误");
        }

        WechatStudentUserBO wechatStudentUserBO = studentUserRepository.getWetChatUserByAccount(Integer.parseInt(account));

        if(wechatStudentUserBO != null && !wechatStudentUserBO.checkEnablePasswordIsCorrect(password)) {
            wechatStudentUserBO.updatePassword(password);
        }if(wechatStudentUserBO == null ) {
            wechatStudentUserBO = studentInfoService.getStudentInfo(account, password);
        }

        if(wechatStudentUserBO == null){
            throw new BusinessException(ErrorCode.ACCOUNT_MISS, account + " 无法获取学号信息");
        }

        return wechatStudentUserBO;
    }
}
