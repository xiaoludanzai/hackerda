package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.*;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.mapper.UserLogoutRecordMapper;
import com.hackerda.platform.infrastructure.database.mapper.ext.TruncateMapper;
import com.hackerda.platform.infrastructure.database.model.UserLogoutRecordExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegisterAppTest {

    @Autowired
    private UserRegisterApp userRegisterApp;
    @Autowired
    private TruncateMapper truncateMapper;
    @Autowired
    private UserRegisterRecordRepository userRegisterRecordRepository;
    @Autowired
    private UserLogoutRecordMapper userLogoutRecordMapper;

    @Test
    public void registerNormal() {

        before();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman,
                "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        AppUserBO account = userRegisterApp.getUserByStudentAccount(studentAccount);

        assertThat(account).isEqualTo(appStudentUserBO);

        List<UserRegisterRecordBO> recordBOList = userRegisterRecordRepository.findByUserName(appStudentUserBO.getUserName());

        assertThat(recordBOList).isNotEmpty();

    }

    @Test
    public void registerInSameAccount() {
        before();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        PhoneNumber phoneNumber1 = new PhoneNumber("17301086276");

        AppStudentUserBO appStudentUserBO1 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber1, Gender.Woman, "test_introduction");

        PhoneNumber phoneNumber2 = new PhoneNumber("17301086277");
        AppStudentUserBO appStudentUserBO2 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber2, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO1, new WechatUser("test_appId", "test_openid"));

        assertThatThrownBy(() -> userRegisterApp.register(appStudentUserBO2, new WechatUser("test_appId",
                "test_openid2")))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("用户手机号或者学号已经被注册");
    }

    @Test
    public void registerInSamePhoneNumber() {
        before();
        StudentAccount studentAccount1 = new StudentAccount("2014025838");
        PhoneNumber phoneNumber = new PhoneNumber("17301086276");

        AppStudentUserBO appStudentUserBO1 = new AppStudentUserBO(studentAccount1, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        StudentAccount studentAccount2 = new StudentAccount("2014025838");
        AppStudentUserBO appStudentUserBO2 = new AppStudentUserBO(studentAccount2, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO1, new WechatUser("test_appId", "test_openid"));

        assertThatThrownBy(() -> userRegisterApp.register(appStudentUserBO2, new WechatUser("test_appId",
                "test_openid2")))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("用户手机号或者学号已经被注册");
    }

    @Test
    public void registerWithSameWechatAfterStudentLogout() {
        before();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        PhoneNumber phoneNumber = new PhoneNumber("17301086276");

        AppStudentUserBO appStudentUserBO1 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");


        AppStudentUserBO appStudentUserBO2 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        AppStudentUserBO appStudentUserBO3 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO1, new WechatUser("test_appId", "test_openid"));

        userRegisterApp.logout("admin", appStudentUserBO1, LogoutType.USER_LOGOUT, LogoutType.USER_LOGOUT.getSource());

        userRegisterApp.register(appStudentUserBO2, new WechatUser("test_appId", "test_openid"));

        userRegisterApp.logout("admin", appStudentUserBO2, LogoutType.USER_LOGOUT, LogoutType.USER_LOGOUT.getSource());

        userRegisterApp.register(appStudentUserBO3, new WechatUser("test_appId", "test_openid"));

        long count = userLogoutRecordMapper.countByExample(new UserLogoutRecordExample());

        assertThat(count).isEqualTo(2);
    }

    private void before(){
        truncateMapper.user();
        truncateMapper.userRole();
        truncateMapper.userStudent();
        truncateMapper.user_register_record();
    }
}