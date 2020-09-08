package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.student.*;
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


    public WechatStudentUserBO getWetChatUserByAccount(int account){

        List<WechatStudentUserDO> wechatUserByAccount = studentUserDao.getWechatUserByAccount(account);

        return studentUserAdapter.toBO(wechatUserByAccount);
    }

    @Override
    public StudentUserBO getByAccount(StudentAccount account) {
        StudentUser studentUser = studentUserDao.selectStudentByAccount(account.getInt());
        return studentUserAdapter.toBO(studentUser);
    }

    public List<WechatStudentUserBO> getByAccountList(Collection<Integer> accountList){

        if(CollectionUtils.isEmpty(accountList)){
            return Collections.emptyList();
        }

        List<WechatStudentUserDO> wechatUserList = studentUserDao.getWechatUserByAccountList(accountList);

        Map<Integer, List<WechatStudentUserDO>> listMap = wechatUserList.stream().collect(Collectors.groupingBy(WechatStudentUserDO::getAccount, Collectors.toList()));

        return listMap.values().stream().map(x-> studentUserAdapter.toBO(x)).collect(Collectors.toList());
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
        if(studentUser.isSaveOrUpdate()) {
            studentUserDao.saveOrUpdate(studentUserAdapter.toDO(studentUser));
        }

        List<StudentWechatBindDetail> openidBOList = studentUser.getWechatOpenidList().stream()
                .filter(StudentWechatBindDetail::isSaveOrUpdate).collect(Collectors.toList());
        for (StudentWechatBindDetail openidBO : openidBOList) {
            wechatOpenIdDao.saveOrUpdate(studentUserAdapter.toDO(openidBO));
        }

    }

}
