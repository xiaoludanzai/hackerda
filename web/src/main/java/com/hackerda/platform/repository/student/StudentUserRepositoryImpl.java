package com.hackerda.platform.repository.student;

import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.dao.WechatOpenIdDao;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
import com.hackerda.platform.pojo.ScheduleTask;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.WechatStudentUserDO;
import com.hackerda.platform.pojo.constant.SubscribeScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentUserRepositoryImpl implements StudentUserRepository {

    @Autowired
    private StudentUserDao studentUserDao;
    @Autowired
    private StudentUserAdapter studentUserAdapter;
    @Autowired
    private WechatOpenIdDao wechatOpenIdDao;


    public StudentUserBO getByAccount(int account){

        List<WechatStudentUserDO> wechatUserByAccount = studentUserDao.getWechatUserByAccount(account);

        return studentUserAdapter.toBO(wechatUserByAccount);
    }

    public List<StudentUserBO> getByAccountList(Collection<Integer> accountList){

        if(CollectionUtils.isEmpty(accountList)){
            return Collections.emptyList();
        }

        List<WechatStudentUserDO> wechatUserList = studentUserDao.getWechatUserByAccountList(accountList);

        Map<Integer, List<WechatStudentUserDO>> listMap = wechatUserList.stream().collect(Collectors.groupingBy(WechatStudentUserDO::getAccount, Collectors.toList()));

        return listMap.values().stream().map(x-> studentUserAdapter.toBO(x)).collect(Collectors.toList());
    }

    public List<StudentUserBO> getSubscribe(SubscribeScene subscribeScene) {
        ScheduleTask task = new ScheduleTask();
        task.setScene(Integer.valueOf(subscribeScene.getScene())).setIsSubscribe((byte) 1);
        Set<Integer> accountSet = wechatOpenIdDao.selectBySubscribe(task).stream()
                .map(WechatOpenid::getAccount).collect(Collectors.toSet());

        return getByAccountList(accountSet);
    }
}
