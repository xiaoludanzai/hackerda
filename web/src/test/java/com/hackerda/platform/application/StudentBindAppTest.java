package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentInfoAssist;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentBindAppTest {

    @Autowired
    private StudentBindApp studentBindApp;
    @Autowired
    private TruncateMapper truncateMapper;
    @Autowired
    private StudentRepository studentRepository;
    @MockBean
    private StudentInfoAssist studentInfoAssist;

    @Before
    public void init(){
        when(studentInfoAssist.inLoginWhiteList(any())).thenReturn(false);
    }

    @Test
    public void bindByOpenId() {

        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        WechatStudentUserBO account = studentRepository.getWetChatUserByAccount(studentAccount);

        assertThat(wechatStudentUserBO).isEqualTo(account);

    }


    @Test
    public void testPasswordUnCorrect() {

        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "2", wechatUser))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("账号或密码错误");
    }

    @Test
    public void testDuplicateBindWithDiffOpenId() {

        truncateMapper.wechatOpenId();

        StudentAccount studentAccount = new StudentAccount("2014025838");

        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId(studentAccount, "1", new WechatUser("test_appId", "test_openid"));


        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", new WechatUser("test_appId", "test_openid_duplicate")))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");

        WechatStudentUserBO account = studentRepository.getWetChatUserByAccount(studentAccount);

        assertThat(wechatStudentUserBO).isEqualTo(account);

    }

    @Test
    public void testDuplicateBindWithDiffOpenIdInWhiteList() {

        when(studentInfoAssist.inLoginWhiteList(any())).thenReturn(true);

        truncateMapper.wechatOpenId();

        StudentAccount studentAccount = new StudentAccount("2014025838");

        studentBindApp.bindByOpenId(studentAccount, "1", new WechatUser("test_appId", "test_openid"));

        studentBindApp.bindByOpenId(studentAccount, "1", new WechatUser("test_appId", "test_openid_duplicate"));

        WechatStudentUserBO account = studentRepository.getWetChatUserByAccount(studentAccount);


    }

    @Test
    public void testDuplicateBindWithSameOpenId() {
        truncateMapper.wechatOpenId();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        WechatStudentUserBO actual = studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);


        WechatStudentUserBO except = studentRepository.getWetChatUserByAccount(studentAccount);

        assertThat(actual).isEqualTo(except);

    }

    @Test
    public void testReBindWith() {

        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);


        studentBindApp.unbindByPlatform(studentRepository.getWetChatUserByAccount(studentAccount), "test_appId");

        WechatStudentUserBO actual = studentBindApp.bindByOpenId(studentAccount, "1", new WechatUser("test_appId", "test_openid_new"));


        WechatStudentUserBO except = studentRepository.getWetChatUserByAccount(studentAccount);

        assertThat(actual).isEqualTo(except);
    }
}