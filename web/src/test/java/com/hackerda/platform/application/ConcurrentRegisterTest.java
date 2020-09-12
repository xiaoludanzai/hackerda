package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.domain.user.UserRegisterAssist;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.infrastructure.database.mapper.ext.TruncateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrentRegisterTest {

    @Autowired
    private UserRegisterApp userRegisterApp;
    @Autowired
    private TruncateMapper truncateMapper;
    @MockBean
    private UserRegisterAssist userRegisterAssist;

    @Test
    public void concurrentRegisterInDiffWechat() {

        /*
        测试当两个用户并行注册时，前面的条件检查通过后，数据库约束是否生效
         */

        before();
        when(userRegisterAssist.userHasRegister(any())).thenReturn(true);
        when(userRegisterAssist.wechatHasRegister(any())).thenReturn(true);

        StudentAccount studentAccount = new StudentAccount("2014025838");
        PhoneNumber phoneNumber1 = new PhoneNumber("17301086276");

        AppStudentUserBO appStudentUserBO1 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber1, Gender.Woman, "test_introduction");

        PhoneNumber phoneNumber2 = new PhoneNumber("17301086277");
        AppStudentUserBO appStudentUserBO2 = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber2, Gender.Woman, "test_introduction");

        userRegisterApp.register(appStudentUserBO1, new WechatUser("test_appId", "test_openid"));

        assertThatThrownBy(() -> userRegisterApp.register(appStudentUserBO2, new WechatUser("test_appId", "test_openid2")))
                .isInstanceOf(DuplicateKeyException.class);

    }

    private void before(){
        truncateMapper.user();
        truncateMapper.userRole();
        truncateMapper.userStudent();
        truncateMapper.user_register_record();
    }
}
