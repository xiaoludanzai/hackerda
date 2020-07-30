package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentUserBO;
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

        StudentUserBO studentUserBO = studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");

        StudentUserBO account = studentUserRepository.getByAccount(2014025838);

        assertThat(studentUserBO).isEqualTo(account);

    }


    @Test
    public void testPasswordUnCorrect() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        StudentUserBO studentUserBO = studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");


        assertThatThrownBy(() -> studentBindApp.bindByOpenId("2014025838", "2", "test_appId", "test_openid"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("账号或密码错误");


    }

    @Test
    public void testDuplicateBindWithDiffOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        StudentUserBO studentUserBO = studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");


        assertThatThrownBy(() -> studentBindApp.bindByOpenId("2014025838", "2", "test_appId", "test_openid_duplicate"))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");


        StudentUserBO account = studentUserRepository.getByAccount(2014025838);

        assertThat(studentUserBO).isEqualTo(account);

    }

    @Test
    public void testDuplicateBindWithSameOpenId() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");

        StudentUserBO actual = studentBindApp.bindByOpenId("2014025838", "1", "test_appId",
                "test_openid");


        StudentUserBO except = studentUserRepository.getByAccount(2014025838);

        assertThat(actual).isEqualTo(except);

    }

    @Test
    public void testReBindWith() {
        truncateMapper.studentUser();
        truncateMapper.wechatOpenId();

        studentBindApp.bindByOpenId("2014025838", "1", "test_appId", "test_openid");


        studentBindApp.unbindByPlatform(studentUserRepository.getByAccount(2014025838), "test_appId");

        StudentUserBO actual = studentBindApp.bindByOpenId("2014025838", "1", "test_appId",
                "test_openid_new");


        StudentUserBO except = studentUserRepository.getByAccount(2014025838);

        assertThat(actual).isEqualTo(except);

    }
}