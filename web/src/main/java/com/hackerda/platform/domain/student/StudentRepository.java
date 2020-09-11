package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.constant.SubscribeScene;

import java.util.Collection;
import java.util.List;

public interface StudentRepository {

    WechatStudentUserBO getWetChatUserByAccount(StudentAccount account);

    StudentUserBO getByAccount(StudentAccount account);

    List<WechatStudentUserBO> getByAccountList(Collection<Integer> accountList);

    List<WechatStudentUserBO> getSubscribe(SubscribeScene subscribeScene);

    void save(WechatStudentUserBO studentUser);

}
