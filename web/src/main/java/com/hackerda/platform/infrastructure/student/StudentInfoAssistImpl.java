package com.hackerda.platform.infrastructure.student;

import com.google.common.collect.Sets;
import com.hackerda.platform.domain.student.Action;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentInfoAssist;
import com.hackerda.platform.domain.user.UserRegisterRecordBO;
import com.hackerda.platform.domain.user.UserRegisterRecordRepository;
import com.hackerda.platform.domain.wechat.ActionRecord;
import com.hackerda.platform.domain.wechat.WechatActionRecordRepository;
import com.hackerda.platform.domain.wechat.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentInfoAssistImpl implements StudentInfoAssist {


    @Autowired
    private UserRegisterRecordRepository userRegisterRecordRepository;
    @Autowired
    private WechatActionRecordRepository wechatActionRecordRepository;
    @Value("${checkWechatCommentUser: false}")
    private boolean checkWechatCommentUser;


    @Override
    public boolean needToCheckWechatCommentUser() {
        return checkWechatCommentUser;
    }


    /**
     * 检查openid是否是学号的常用openid
     * @param account 教务网账号
     * @param wechatUser 微信用户
     * @return 是常用微信则返回true
     */
    @Override
    public boolean isCommonWechat(StudentAccount account, WechatUser wechatUser) {

        List<UserRegisterRecordBO> recordList = userRegisterRecordRepository.findByStudentAccount(account);

        List<UserRegisterRecordBO> filterList = recordList.stream()
                .filter(x -> x.getWechatUser().equals(wechatUser))
                .collect(Collectors.toList());

        if(!filterList.isEmpty() && filterList.size() % 2 != 0) {
            return true;
        }

        List<ActionRecord> authLoginList = wechatActionRecordRepository.find(wechatUser).stream()
                .filter(x -> x.getStudentAccount().equals(account))
                .filter(x -> x.getAction() == Action.AuthLogin)
                .collect(Collectors.toList());

        return !authLoginList.isEmpty();
    }
}
