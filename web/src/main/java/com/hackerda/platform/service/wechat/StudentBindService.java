package com.hackerda.platform.service.wechat;

import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.dao.WechatBindRecordDao;
import com.hackerda.platform.dao.WechatOpenIdDao;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.UrpClass;
import com.hackerda.platform.pojo.WechatBindRecord;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.vo.StudentVo;
import com.hackerda.platform.service.ClassService;
import com.hackerda.platform.service.NewUrpSpiderService;
import com.hackerda.platform.utils.DESUtil;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author junrong.chen
 * @date 2018/10/13
 */
@Service("studentBindService")
@Slf4j
public class StudentBindService {
    @Resource
    private WechatOpenIdDao wechatOpenIdDao;
    @Resource
    private NewUrpSpiderService newUrpSpiderService;


    private static final String TEXT_LINK = "<a href=\"%s\">%s</a>";
    @Resource
    private WechatBindRecordDao wechatBindRecordDao;
    @Resource
    private ClassService classService;
    @Resource
    private StudentUserDao studentUserDao;
    @Value("${student.password.salt}")
    private String key;

    /**
     * 学号与微信公众平台openID关联
     * <p>
     * 现在的一个问题是，如果是从一次订阅的接口路由过来的用户，如何帮他们实现快速绑定呢？
     * 点击地址以后将openID存在session中，查看是否已经绑定
     * <p>
     * 1.数据库中有学生信息，并且密码不正确的，重新通过教务网确认密码
     * 2.数据库中有学生信息且密码正确，直接绑定
     * 3.数据库中没有学生数据，查询后绑定
     *
     * @param openid   微信用户唯一标识
     * @param account  学生教务网账号
     * @param password 学生教务网密码
     * @throws PasswordUnCorrectException 密码不正确异常
     */
    public StudentVo studentBind(String openid, String account, String password, String appid) {

        StudentVo studentVo = studentLogin(account, password);

        studentBind(account, openid, appid);

        return studentVo;

    }

    /**
     * 用于学生从非微信渠道登录
     *
     * @param account        账号
     * @param enablePassword 密码
     * @return 学生信息
     */
    public StudentVo studentLogin(String account, String enablePassword) throws PasswordUnCorrectException {

        StudentUser student = studentUserDao.selectStudentByAccount(Integer.parseInt(account));
        if (student != null) {
            String encrypt = DESUtil.encrypt(enablePassword, account + key);
            if (!student.getIsCorrect() || !student.getPassword().equals(encrypt)) {
                newUrpSpiderService.checkStudentPassword(account, enablePassword);
                studentUserDao.updatePassword(account, encrypt);
            }
        } else {
            student = getStudentUserInfo(account, enablePassword);
            studentUserDao.insertStudentSelective(student);
        }

        return new StudentVo(student, student.getAccount().toString() + key);
    }

    /**
     * 如果已经存在openid则更新关联的学号
     * 并且插入一条更新的记录
     * <p>
     * 否则插入一条新数据再绑定
     *
     * @param account 学号
     * @param openid  微信用户唯一标识
     * @param appid   微信平台对应的id
     */
    void studentBind(String account, String openid, String appid) {
        WechatOpenid wechatOpenid = wechatOpenIdDao.selectByUniqueKey(appid, openid);
        if (wechatOpenid != null) {
            wechatOpenIdDao.updateByPrimaryKeySelective(
                    wechatOpenid.setAccount(Integer.parseInt(account)).setGmtModified(new Date()).setIsBind(true));
            if (!account.equals(wechatOpenid.getAccount().toString())) {
                wechatBindRecordDao.insertSelective(new WechatBindRecord()
                        .setOriginAccount(wechatOpenid.getAccount().toString())
                        .setUpdateAccount(account)
                        .setAppid(appid).setOpenid(openid));
            }
        } else {
            wechatOpenIdDao.insertSelective(new WechatOpenid()
                    .setAccount(Integer.parseInt(account))
                    .setOpenid(openid)
                    .setAppid(appid));
        }

    }



    public StudentUser getStudentUserInfo(String account, String password) {
        StudentUser userInfo = newUrpSpiderService.getStudentUserInfo(account, password);
        UrpClass urpClass = classService.getClassByName(userInfo.getClassName(), userInfo.getAccount().toString());
        userInfo.setUrpclassNum(Integer.parseInt(urpClass.getClassNum()));

        return userInfo;

    }


    public String getTextLink(String url, String content) {
        return String.format(TEXT_LINK, url, content);
    }

}
