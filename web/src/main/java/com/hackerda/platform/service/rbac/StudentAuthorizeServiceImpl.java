package com.hackerda.platform.service.rbac;

import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.dao.WechatBindRecordDao;
import com.hackerda.platform.dao.WechatOpenIdDao;
import com.hackerda.platform.pojo.*;
import com.hackerda.platform.pojo.vo.StudentUserDetailVo;
import com.hackerda.platform.service.ClassService;
import com.hackerda.platform.service.NewUrpSpiderService;
import com.hackerda.platform.utils.DESUtil;
import com.hackerda.platform.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @author JR Chan
 */
@Service
public class StudentAuthorizeServiceImpl implements UserAuthorizeService{

    @Resource
    private WechatOpenIdDao wechatOpenIdDao;
    @Resource
    private NewUrpSpiderService newUrpSpiderService;
    @Resource
    private WechatBindRecordDao wechatBindRecordDao;
    @Resource
    private StudentUserDao studentUserDao;
    @Resource
    private ClassService classService;
    @Value("${student.password.salt}")
    private String key;
    @Autowired
    private UserDetailService userDetailService;


    /**
     * 用于学生从非微信渠道登录
     *
     * @param account        账号
     * @param enablePassword 密码
     * @return 学生信息
     */
    public StudentUser studentLogin(String account, String enablePassword) throws PasswordUnCorrectException {

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

        return student;
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


    @Override
    public StudentUserDetailVo studentAuthorize(@Nonnull String account, @Nonnull String password, @Nullable String appId, @Nullable String openid) {
        StudentUser studentUser = studentLogin(account, password);
        if(StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(openid)){
            studentBind(account, openid, appId);
        }

        StudentUserDetail studentUserDetail = userDetailService.getStudentUserDetail(studentUser.getAccount().toString());

        String[] permission = studentUserDetail.getPermissionSet().toArray(new String[0]);
        String[] role = studentUserDetail.getRoleSet().toArray(new String[0]);

        String token = JwtUtils.signForUserDetail(account, role, permission, account);

        return new StudentUserDetailVo(studentUser, studentUserDetail, token);
    }
}
