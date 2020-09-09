package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentInfoAssist;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.mapper.ext.TruncateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentUserCommonUserBindTest {

    @Autowired
    private UserRegisterApp userRegisterApp;
    @Autowired
    private StudentBindApp studentBindApp;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TruncateMapper truncateMapper;

    @MockBean
    private StudentInfoAssist studentInfoAssist;

    @Before
    public void init(){
        when(studentInfoAssist.inLoginWhiteList(any())).thenReturn(false);
        when(studentInfoAssist.needToCheckWechatCommentUser()).thenReturn(true);
    }

    @Test
    public void commonUserCheckInNormal() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，解绑，再登录同一个账号，不需要检查常用微信用户
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        WechatStudentUserBO wechatStudentUserBO = studentRepository.getWetChatUserByAccount(studentAccount.getInt());
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");
    }


    @Test
    public void commonUserCheckInUnBindFail() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，
        解绑失败，此时openid与账号还是绑定状态，再登录同一个账号，不需要检查常用微信用户
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");
    }

    @Test
    public void unCommonUserCheckInBind() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，
        解绑失败，此时openid与账号还是绑定状态，不同的openid无法登录该账号
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", "test_appId",
                "uncommon_user_openid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");

    }

    @Test
    public void unCommonUserCheckInUnbind() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，然后解绑
        未绑定过该学号用户登录，需要检查是否是常用微信用户
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        WechatStudentUserBO wechatStudentUserBO = studentRepository.getWetChatUserByAccount(studentAccount.getInt());
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");

        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", "test_appId",
                "uncommon_user_openid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("非常用微信号登录");
    }

    @Test
    public void unCommonUserCheckInBindOther() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，然后解绑后绑定其它用户
        需要检查是否是常用微信用户
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        WechatStudentUserBO wechatStudentUserBO = studentRepository.getWetChatUserByAccount(studentAccount.getInt());
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");

        StudentAccount otherAccount = new StudentAccount("2017025839");

        studentBindApp.bindByOpenId(otherAccount, "1", "test_appId", "test_openid");

        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", "test_appId",
                "test_openid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("非常用微信号登录");
    }

    @Test
    public void testWhiteList() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，然后解绑后绑定其它用户
        需要检查是否是常用微信用户
         */
        before();
        when(studentInfoAssist.inLoginWhiteList(any())).thenReturn(true);

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        WechatStudentUserBO wechatStudentUserBO = studentRepository.getWetChatUserByAccount(studentAccount.getInt());
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "uncommon_user_openid");

    }


    private void before(){
        truncateMapper.user();
        truncateMapper.userRole();
        truncateMapper.userStudent();
        truncateMapper.wechatOpenId();
    }
}
