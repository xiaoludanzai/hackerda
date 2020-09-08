package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.student.StudentWechatBindDetail;
import com.hackerda.platform.domain.student.WechatSubscribeBO;
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

    @Autowired
    private Map<String , WechatPlatform> wechatPlatformMap;
    @Value("${student.password.salt}")
    private String key;

    public WechatStudentUserBO toBO(List<WechatStudentUserDO> wechatStudentUserDOList){

        if(CollectionUtils.isEmpty(wechatStudentUserDOList)) {
           return null;
        }

        WechatStudentUserDO studentUser = wechatStudentUserDOList.get(0);

        Map<StudentWechatBindDetail, List<WechatSubscribeBO>> listMap = wechatStudentUserDOList.stream()
                .collect(Collectors.groupingBy(x -> new StudentWechatBindDetail(studentUser.getAccount(), x.getOpenId(),
                                x.getIsBind(),
                                x.getAppId(),
                                wechatPlatformMap.get(x.getAppId()), false),
                        Collectors.mapping(x -> new WechatSubscribeBO(x.getIsSubscribe(), x.getScene()), Collectors.toList())));

        for (StudentWechatBindDetail openidBO : listMap.keySet()) {
            openidBO.setWechatSubscribeBOList(listMap.getOrDefault(openidBO,
                    Collections.emptyList()).stream().filter(WechatSubscribeBO::isNotNull).collect(Collectors.toList()));
        }

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
        bo.setWechatOpenidList(new ArrayList<>(listMap.keySet()));

        bo.setKey(key);

        return bo;
    }


    public StudentUserBO toBO(StudentUser studentUser) {

        if(studentUser == null) {
            return null;
        }

        StudentUserBO user = new StudentUserBO();

        user.setAccount(studentUser.getAccount());
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

        return user;

    }

    public StudentUser toDO(WechatStudentUserBO wechatStudentUserBO){
        StudentUser user = new StudentUser();

        user.setAccount(wechatStudentUserBO.getAccount());
        user.setPassword(wechatStudentUserBO.getPassword());
        user.setIsCorrect(wechatStudentUserBO.getIsCorrect());
        user.setUrpclassNum(wechatStudentUserBO.getUrpClassNum());
        user.setAcademyName(wechatStudentUserBO.getAcademyName());
        user.setClassName(wechatStudentUserBO.getClassName());
        user.setEthnic(wechatStudentUserBO.getEthnic());
        user.setSubjectName(wechatStudentUserBO.getSubjectName());
        user.setSex(wechatStudentUserBO.getSex());
        user.setName(wechatStudentUserBO.getName());

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
