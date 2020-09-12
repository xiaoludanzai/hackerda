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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class StudentUserCommonUserBindTest {

    @Autowired
    private UserRegisterApp userRegisterApp;
    @Autowired
    private StudentBindApp studentBindApp;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TruncateMapper truncateMapper;
    @Autowired
    private StudentInfoAssist studentInfoAssist;


    @Before
    public void init() {

        StudentInfoAssist infoAssist = new StudentInfoAssist() {
            @Override
            public boolean needToCheckWechatCommentUser() {
                return true;
            }

            @Override
            public boolean isCommonWechat(StudentAccount account, WechatUser wechatUser) {
                return StudentUserCommonUserBindTest.this.studentInfoAssist.isCommonWechat(account, wechatUser);
            }
        };
        studentBindApp.setStudentInfoAssist(infoAssist);
    }


    @Test
    public void commonUserCheckInNormal() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，解绑，再登录同一个账号，不需要检查常用微信用户
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        WechatStudentUserBO wechatStudentUserBO = studentRepository.findWetChatUser(studentAccount);
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);
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

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);
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

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);
        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1",  new WechatUser("test_appId",
                "uncommon_user_openid")))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");

    }

    @Test
    public void unCommonUserSameAccount() {
        /*
        对常用微信用户的测试，一个用户绑定后再注册科大圈，然后解绑
        未绑定过该学号用户登录，需要检查是否是常用微信用户
         */
        before();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        // 2014025838先绑定后注册
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        // 注册完成后2014025838解绑  新的微信账号账号授权后可登录
        WechatStudentUserBO wechatStudentUserBO = studentRepository.findWetChatUser(studentAccount);
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");
        WechatUser unCommonUser = new WechatUser("test_appId", "uncommon_user_openid");
        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", unCommonUser))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("非常用微信号登录");

        // 微信2授权绑定 2014025838
        studentBindApp.bindCommonWechatUser(studentAccount, phoneNumber, unCommonUser);
        studentBindApp.bindByOpenId(studentAccount, "1", unCommonUser);

        assertThat(studentRepository.findWetChatUser(studentAccount).hasBindWechatUser(unCommonUser)).isTrue();
        assertThat(studentRepository.findWetChatUser(studentAccount).hasBindWechatUser(wechatUser)).isFalse();


        // 微信2当前绑定了 2014025838 所以无法注册成功
        PhoneNumber phoneNumber2 = new PhoneNumber("17301086277");
        StudentAccount studentAccount2 = new StudentAccount("2017025838");
        AppStudentUserBO appStudentUserBO2 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber2, Gender.Woman, "test_introduction");

        // 微信2当前绑定了2014025838  2014025838已经注册了科大圈,微信2无法注册
        assertThatThrownBy(() -> userRegisterApp.register(appStudentUserBO2, unCommonUser))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("用户手机号或者学号已经被注册");


        // 微信2 切换绑定到 2017025838 注册一个新的科大圈身份
        studentBindApp.bindByOpenId(studentAccount2, "1", unCommonUser);
        AppStudentUserBO appStudentUserBO3 = new AppStudentUserBO(studentAccount2, "test2", "1", "test_avatarPath",
                phoneNumber2, Gender.Woman, "test_introduction");
        userRegisterApp.register(appStudentUserBO3, unCommonUser);

        assertThat(userRegisterApp.getUserByStudentAccount(studentAccount2)).isEqualTo(appStudentUserBO3);


        // 注册完后又重新绑定 2014025838 已经是常用用户 无需授权
        studentBindApp.bindByOpenId(studentAccount, "1", unCommonUser);
        assertThat(studentRepository.findWetChatUser(studentAccount).hasBindWechatUser(unCommonUser)).isTrue();
        assertThat(studentRepository.findWetChatUser(studentAccount).hasBindWechatUser(wechatUser)).isFalse();
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

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO, wechatUser);

        WechatStudentUserBO wechatStudentUserBO = studentRepository.findWetChatUser(studentAccount);
        studentBindApp.unbindByPlatform(wechatStudentUserBO, "test_appId");

        StudentAccount otherAccount = new StudentAccount("2017025839");

        studentBindApp.bindByOpenId(otherAccount, "1", wechatUser);

        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

    }



    private void before(){
        truncateMapper.user();
        truncateMapper.userRole();
        truncateMapper.userStudent();
        truncateMapper.wechatOpenId();
        truncateMapper.user_register_record();
    }
}
