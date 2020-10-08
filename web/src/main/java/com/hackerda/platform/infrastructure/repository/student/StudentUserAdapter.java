package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.WechatOpenid;
import com.hackerda.platform.infrastructure.database.model.WechatStudentUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentUserAdapter {


    @Value("${student.password.salt}")
    private String key;


    public WechatStudentUserBO toBO(StudentUser studentUser) {

        if(studentUser == null) {
            return null;
        }

        WechatStudentUserBO user = new WechatStudentUserBO();

        user.setAccount(new StudentAccount(studentUser.getAccount()));
        user.setPassword(studentUser.getPassword());
        user.setIsCorrect(studentUser.getIsCorrect());
        user.setUrpClassNum(studentUser.getUrpclassNum());
        user.setAcademyName(studentUser.getAcademyName());
        user.setClassName(studentUser.getClassName());
        user.setEthnic(studentUser.getEthnic());
        user.setSubjectName(studentUser.getSubjectName());
        user.setSex(studentUser.getSex());
        user.setName(studentUser.getName());
        user.setKey(key);
        user.setMsgHasCheck(studentUser.getHasCheck());

        return user;

    }

    public StudentUser toDO(WechatStudentUserBO wechatStudentUserBO){
        StudentUser user = new StudentUser();

        user.setAccount(wechatStudentUserBO.getAccount().getInt());
        user.setPassword(wechatStudentUserBO.getPassword());
        user.setIsCorrect(wechatStudentUserBO.getIsCorrect());
        user.setUrpclassNum(wechatStudentUserBO.getUrpClassNum());
        user.setAcademyName(wechatStudentUserBO.getAcademyName());
        user.setClassName(wechatStudentUserBO.getClassName());
        user.setEthnic(wechatStudentUserBO.getEthnic());
        user.setSubjectName(wechatStudentUserBO.getSubjectName());
        user.setSex(wechatStudentUserBO.getSex());
        user.setName(wechatStudentUserBO.getName());
        user.setHasCheck(wechatStudentUserBO.isMsgHasCheck());

        return user;
    }

    public WechatOpenid toDO(StudentWechatBindDetail studentWechatBindDetail){
        WechatOpenid wechatOpenid = new WechatOpenid();

        wechatOpenid.setAccount(studentWechatBindDetail.getAccount());
        wechatOpenid.setIsBind(studentWechatBindDetail.isBind());
        wechatOpenid.setAppid(studentWechatBindDetail.getAppId());
        wechatOpenid.setOpenid(studentWechatBindDetail.getOpenid());

        return wechatOpenid;
    }


}
