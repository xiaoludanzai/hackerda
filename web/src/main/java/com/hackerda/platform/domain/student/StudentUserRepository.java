package com.hackerda.platform.domain.student;

import com.hackerda.platform.pojo.constant.SubscribeScene;

import java.util.Collection;
import java.util.List;

public interface StudentUserRepository {

    StudentUserBO getByAccount(int account);

    List<StudentUserBO> getByAccountList(Collection<Integer> accountList);

    List<StudentUserBO> getSubscribe(SubscribeScene subscribeScene);
}
