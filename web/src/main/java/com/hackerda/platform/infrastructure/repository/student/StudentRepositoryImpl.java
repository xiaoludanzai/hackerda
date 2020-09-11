package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.infrastructure.database.dao.StudentUserDao;
import com.hackerda.platform.infrastructure.database.dao.WechatOpenIdDao;
import com.hackerda.platform.infrastructure.database.model.ScheduleTask;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.WechatOpenid;
import com.hackerda.platform.infrastructure.database.model.WechatStudentUserDO;
import com.hackerda.platform.domain.constant.SubscribeScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private StudentUserDao studentUserDao;
    @Autowired
    private StudentUserAdapter studentUserAdapter;
    @Autowired
    private WechatOpenIdDao wechatOpenIdDao;


    public WechatStudentUserBO findWetChatUser(StudentAccount account){

        StudentUser studentUser = studentUserDao.selectStudentByAccount(account.getInt());
        WechatStudentUserBO wechatStudentUserBO = studentUserAdapter.toBO(studentUser);
        List<WechatUser> wechatUserList = wechatOpenIdDao.selectBindWechat(account.getInt()).stream()
                .map(x -> new WechatUser(x.getAppId(), x.getOpenId()))
                .collect(Collectors.toList());

        wechatStudentUserBO.setBindWechatUser(wechatUserList);

        return wechatStudentUserBO;
    }

    @Override
    public StudentUserBO find(StudentAccount account) {
        StudentUser studentUser = studentUserDao.selectStudentByAccount(account.getInt());
        return studentUserAdapter.toBO(studentUser);
    }

    public List<WechatStudentUserBO> getByAccountList(Collection<Integer> accountList){
        // TODO implement

        return Collections.emptyList();
    }

    public List<WechatStudentUserBO> getSubscribe(SubscribeScene subscribeScene) {
        ScheduleTask task = new ScheduleTask();
        task.setScene(Integer.valueOf(subscribeScene.getScene())).setIsSubscribe((byte) 1);
        Set<Integer> accountSet = wechatOpenIdDao.selectBySubscribe(task).stream()
                .map(WechatOpenid::getAccount).collect(Collectors.toSet());

        return getByAccountList(accountSet);
    }

    @Override
    @Transactional
    public void save(WechatStudentUserBO studentUser) {
        // TODO implement

        if(studentUser.isSaveOrUpdate()) {
            studentUserDao.saveOrUpdate(studentUserAdapter.toDO(studentUser));
        }


    }

}
