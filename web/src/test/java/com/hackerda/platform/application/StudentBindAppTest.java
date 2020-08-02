package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.mapper.ext.TruncateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@ActiveProfiles("beta")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentBindAppTest {

    @Autowired
    private StudentBindApp studentBindApp;
    @Autowired
    private TruncateMapper truncateMapper;

    @Autowired
    private StudentUserRepository studentUserRepository;


    @Test
    public void bindByOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");

        WechatStudentUserBO account = studentUserRepository.getWetChatUserByAccount(2014025838);

        assertThat(wechatStudentUserBO).isEqualTo(account);

    }


    @Test
    public void testPasswordUnCorrect() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");


        assertThatThrownBy(() -> studentBindApp.bindByOpenId("2014025838", "2", "test_appId", "test_openid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("账号或密码错误");


    }

    @Test
    public void testDuplicateBindWithDiffOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");


        assertThatThrownBy(() -> studentBindApp.bindByOpenId("2014025838", "2", "test_appId", "test_openid_duplicate"))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");


        WechatStudentUserBO account = studentUserRepository.getWetChatUserByAccount(2014025838);

        assertThat(wechatStudentUserBO).isEqualTo(account);

    }

    @Test
    public void testDuplicateBindWithSameOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");

        WechatStudentUserBO actual = studentBindApp.bindByOpenId("2014025838", "1", "test_appId",
                "test_openid");


        WechatStudentUserBO except = studentUserRepository.getWetChatUserByAccount(2014025838);

        assertThat(actual).isEqualTo(except);

    }

    @Test
    public void testReBindWith() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");


        studentBindApp.unbindByPlatform(studentUserRepository.getWetChatUserByAccount(2014025838), "test_appId");

        WechatStudentUserBO actual = studentBindApp.bindByOpenId("2014025838", "1", "test_appId",
                "test_openid_new");


        WechatStudentUserBO except = studentUserRepository.getWetChatUserByAccount(2014025838);

        assertThat(actual).isEqualTo(except);

    }
}