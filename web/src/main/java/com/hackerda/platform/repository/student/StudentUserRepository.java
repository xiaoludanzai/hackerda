package com.hackerda.platform.repository.student;

import com.hackerda.platform.dao.ScheduleTaskDao;
import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.dao.WechatOpenIdDao;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.WechatOpenid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUserRepository {

    @Autowired
    private StudentUserDao studentUserDao;
    @Autowired
    private WechatOpenIdDao wechatOpenIdDao;
    @Autowired
    private StudentUserAdapter studentUserAdapter;


    public StudentUserBO getByAccount(int account){

        StudentUser studentUser = studentUserDao.selectStudentByAccount(account);

        StudentUserBO bo = studentUserAdapter.toBO(studentUser);

        WechatOpenid openid = new WechatOpenid();
        openid.setAccount(account);

        List<WechatOpenid> openidList = wechatOpenIdDao.selectByPojo(openid);

        bo.setWechatOpenidList(openidList);


        return bo;
    }
}
