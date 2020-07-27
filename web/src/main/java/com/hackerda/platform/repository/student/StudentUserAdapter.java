package com.hackerda.platform.repository.student;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatOpenidBO;
import com.hackerda.platform.domain.student.WechatSubscribeBO;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.WechatStudentUserDO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public StudentUserBO toBO(List<WechatStudentUserDO> wechatStudentUserDOList){

        if(CollectionUtils.isEmpty(wechatStudentUserDOList)) {
           return null;
        }

        WechatStudentUserDO studentUser = wechatStudentUserDOList.get(0);

        Map<WechatOpenidBO, List<WechatSubscribeBO>> listMap = wechatStudentUserDOList.stream()
                .collect(Collectors.groupingBy(x -> new WechatOpenidBO(studentUser.getAccount(), x.getOpenId(),
                                x.getIsBind(),
                                x.getAppId(),
                                wechatPlatformMap.get(x.getAppId()), false),
                        Collectors.mapping(x -> new WechatSubscribeBO(x.getIsSubscribe(), x.getScene()), Collectors.toList())));

        for (WechatOpenidBO openidBO : listMap.keySet()) {
            openidBO.setWechatSubscribeBOList(listMap.getOrDefault(openidBO,
                    Collections.emptyList()).stream().filter(WechatSubscribeBO::isNotNull).collect(Collectors.toList()));
        }

        StudentUserBO bo = new StudentUserBO();

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

        return bo;
    }

    public StudentUser toDO(StudentUserBO studentUserBO){
        StudentUser user = new StudentUser();

        user.setAccount(studentUserBO.getAccount());
        user.setPassword(studentUserBO.getPassword());
        user.setIsCorrect(studentUserBO.getIsCorrect());
        user.setUrpclassNum(studentUserBO.getUrpClassNum());
        user.setAcademyName(studentUserBO.getAcademyName());
        user.setClassName(studentUserBO.getClassName());
        user.setEthnic(studentUserBO.getEthnic());
        user.setSubjectName(studentUserBO.getSubjectName());
        user.setSex(studentUserBO.getSex());
        user.setName(studentUserBO.getName());

        return user;
    }

    public WechatOpenid toDO(WechatOpenidBO wechatOpenidBO){
        WechatOpenid wechatOpenid = new WechatOpenid();

        wechatOpenid.setAccount(wechatOpenidBO.getAccount());
        wechatOpenid.setIsBind(wechatOpenid.getIsBind());
        wechatOpenid.setAppid(wechatOpenid.getAppid());
        wechatOpenid.setOpenid(wechatOpenidBO.getOpenid());

        return wechatOpenid;
    }


}
