package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.dao.StudentUserDao;
import com.hackerda.platform.infrastructure.database.dao.WechatOpenIdDao;
import com.hackerda.platform.infrastructure.database.mapper.WechatOpenidStudentRelativeMapper;
import com.hackerda.platform.infrastructure.database.model.*;
import com.hackerda.platform.domain.constant.SubscribeScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    @Autowired
    private WechatOpenidStudentRelativeMapper wechatOpenidStudentRelativeMapper;


    public WechatStudentUserBO findWetChatUser(StudentAccount account){

        StudentUser studentUser = studentUserDao.selectStudentByAccount(account.getInt());

        if(studentUser == null) {
            return null;
        }
        WechatStudentUserBO wechatStudentUserBO = studentUserAdapter.toBO(studentUser);

        WechatOpenidStudentRelativeExample example = new WechatOpenidStudentRelativeExample();
        example.createCriteria().andAccountEqualTo(studentUser.getAccount());

        List<WechatUser> wechatUserList = wechatOpenidStudentRelativeMapper.selectByExample(example).stream()
                .map(x -> new WechatUser(x.getAppid(), x.getOpenid()))
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

        if(studentUser.isSaveOrUpdate()) {
            studentUserDao.saveOrUpdate(studentUserAdapter.toDO(studentUser));
        }

        for (WechatUser wechatUser : studentUser.getNewBindWechatUser()) {

            WechatOpenidStudentRelativeExample example = new WechatOpenidStudentRelativeExample();
            example.createCriteria()
                    .andAppidEqualTo(wechatUser.getAppId())
                    .andOpenidEqualTo(wechatUser.getOpenId());

            WechatOpenidStudentRelative orElse = wechatOpenidStudentRelativeMapper.selectByExample(example).stream().findFirst().orElse(null);


            if(orElse != null) {
                orElse.setAccount(studentUser.getAccount().getInt());
                wechatOpenidStudentRelativeMapper.updateByPrimaryKeySelective(orElse);
            } else {
                WechatOpenidStudentRelative relative = new WechatOpenidStudentRelative();
                relative.setAccount(studentUser.getAccount().getInt());
                relative.setAppid(wechatUser.getAppId());
                relative.setOpenid(wechatUser.getOpenId());
                try {
                    wechatOpenidStudentRelativeMapper.insertSelective(relative);
                }catch (DuplicateKeyException e) {
                    throw new BusinessException(e, ErrorCode.ACCOUNT_HAS_BIND, "重复插入主键");
                }

            }

        }

        for (WechatUser wechatUser : studentUser.getRevokeWechatUser()) {
            WechatOpenidStudentRelativeExample example = new WechatOpenidStudentRelativeExample();
            example.createCriteria().andAccountEqualTo(studentUser.getAccount().getInt())
                    .andAppidEqualTo(wechatUser.getAppId());

            wechatOpenidStudentRelativeMapper.deleteByExample(example);
        }

    }

}
