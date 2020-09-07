package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentInfoAssist;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
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
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");

        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        WechatStudentUserBO account = studentRepository.getWetChatUserByAccount(2014025838);

        assertThat(wechatStudentUserBO).isEqualTo(account);

    }


    @Test
    public void testPasswordUnCorrect() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "2", "test_appId", "test_openid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("账号或密码错误");
    }

    @Test
    public void testDuplicateBindWithDiffOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");


        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", "test_appId",
                "test_openid_duplicate"))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");

        WechatStudentUserBO account = studentRepository.getWetChatUserByAccount(2014025838);

        assertThat(wechatStudentUserBO).isEqualTo(account);

    }

    @Test
    public void testDuplicateBindWithDiffOpenIdInWhiteList() {

        when(studentInfoAssist.inLoginWhiteList(any())).thenReturn(true);
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        StudentAccount studentAccount = new StudentAccount("2014025838");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid_duplicate");

        WechatStudentUserBO account = studentRepository.getWetChatUserByAccount(2014025838);

        assertThat(account.getWechatOpenidList().size()).isEqualTo(2);

    }

    @Test
    public void testDuplicateBindWithSameOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");

        WechatStudentUserBO actual = studentBindApp.bindByOpenId(studentAccount, "1", "test_appId",
                "test_openid");


        WechatStudentUserBO except = studentRepository.getWetChatUserByAccount(2014025838);

        assertThat(actual).isEqualTo(except);

    }

    @Test
    public void testReBindWith() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();
        StudentAccount studentAccount = new StudentAccount("2014025838");
        studentBindApp.bindByOpenId(studentAccount, "1", "test_appId", "test_openid");


        studentBindApp.unbindByPlatform(studentRepository.getWetChatUserByAccount(2014025838), "test_appId");

        WechatStudentUserBO actual = studentBindApp.bindByOpenId(studentAccount, "1", "test_appId",
                "test_openid_new");


        WechatStudentUserBO except = studentRepository.getWetChatUserByAccount(2014025838);

        assertThat(actual).isEqualTo(except);
    }
}