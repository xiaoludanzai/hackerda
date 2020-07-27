package com.hackerda.platform.application;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.student.StudentInfoService;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
import com.hackerda.platform.pojo.wechat.miniprogram.AuthResponse;
import com.hackerda.platform.service.wechat.MiniProgramService;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class StudentBindApp {

    @Autowired
    private MiniProgramService miniProgramService;
    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private StudentInfoService studentInfoService;


    public StudentUserBO bindByCode(@Nonnull String account, @Nonnull String password, @Nonnull String appId,
                               @Nonnull String code){
        // 查询对应的openid
        AuthResponse auth = miniProgramService.auth(code);

        return bindByOpenId(account, password, appId, auth.getOpenid());
    }

    public StudentUserBO bindByOpenId(@Nonnull String account, @Nonnull String password, @Nonnull String appId,
                                      @Nonnull String openid) {

        if(studentInfoService.checkCanBind(account, openid)) {
            StudentUserBO studentUserBO = getStudentUserBO(account, password);

            studentUserBO.bindWechatPlatform(openid, appId, WechatPlatform.HKXJ_APP);

            studentUserRepository.save(studentUserBO);

            return studentUserBO;

        }else {
            throw new RuntimeException("该学号已经被绑定");
        }
    }



    private StudentUserBO getStudentUserBO(@Nonnull String account, @Nonnull String password) {
        if(!studentInfoService.checkPasswordValid(account, password)){
            throw new PasswordUnCorrectException();
        }

        StudentUserBO studentUserBO = studentUserRepository.getByAccount(Integer.parseInt(account));

        if(studentUserBO != null && !studentUserBO.checkEnablePasswordIsCorrect(password)) {
            studentUserBO.updatePassword(password);
        }if(studentUserBO == null ) {
            studentUserBO = studentInfoService.getStudentInfo(account, password);
        }

        if(studentUserBO == null){
            throw new RuntimeException(account + " 无法获取信息");
        }

        return studentUserBO;
    }
}
