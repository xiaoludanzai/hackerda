package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.wechat.WechatActionRecordRepository;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.mapper.ext.TruncateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @Autowired
    private WechatActionRecordRepository wechatActionRecordRepository;

    @Test
    public void bindByOpenId() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();
        StudentAccount studentAccount = new StudentAccount("2017025820");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        WechatStudentUserBO account = studentRepository.findWetChatUser(studentAccount);

        assertThat(wechatStudentUserBO).isEqualTo(account);

        studentBindApp.unbindByPlatform(studentRepository.findWetChatUser(studentAccount), "test_appId");

        assertThat(studentRepository.findWetChatUser(studentAccount).hasBindWechatUser()).isFalse();

        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(1);


        assertThat(wechatStudentUserBO.isMsgHasCheck()).isTrue();

    }


    @Test
    public void testPasswordUnCorrect() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "2", wechatUser))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("账号或密码错误");

        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(2);

        WechatStudentUserBO studentUserBO = studentRepository.findWetChatUser(studentAccount);

        assertThat(studentUserBO.getIsCorrect()).isFalse();
        assertThat(studentUserBO.hasBindWechatUser()).isFalse();
    }

    @Test
    public void testDuplicateBindWithDiffOpenId() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        WechatStudentUserBO wechatStudentUserBO = studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        WechatUser duplicate = new WechatUser("test_appId", "test_openid_duplicate");
        assertThatThrownBy(() -> studentBindApp.bindByOpenId(studentAccount, "1", duplicate))
                .isInstanceOf(BusinessException.class)
                .hasMessageEndingWith("该学号已经被绑定");

        WechatStudentUserBO account = studentRepository.findWetChatUser(studentAccount);

        assertThat(wechatStudentUserBO).isEqualTo(account);
        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(1);
        assertThat(wechatActionRecordRepository.find(duplicate).size()).isEqualTo(1);

    }


    @Test
    public void testDuplicateBindWithSameOpenId() {
        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);

        WechatStudentUserBO actual = studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);


        WechatStudentUserBO except = studentRepository.findWetChatUser(studentAccount);

        assertThat(actual).isEqualTo(except);
        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(2);

    }

    @Test
    public void testReBindWith() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();

        StudentAccount studentAccount = new StudentAccount("2014025838");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);


        studentBindApp.unbindByPlatform(studentRepository.findWetChatUser(studentAccount), "test_appId");
        WechatUser newBind = new WechatUser("test_appId", "test_openid_new");
        WechatStudentUserBO actual = studentBindApp.bindByOpenId(studentAccount, "1", newBind);


        WechatStudentUserBO except = studentRepository.findWetChatUser(studentAccount);

        assertThat(actual).isEqualTo(except);
        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(1);
        assertThat(wechatActionRecordRepository.find(newBind).size()).isEqualTo(1);
    }

    @Test
    public void testReBindAfterRevokeFailed() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();


        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(new StudentAccount("2014025838"), "1", wechatUser);

        WechatStudentUserBO actual = studentBindApp.bindByOpenId(new StudentAccount("2017025838"), "1", wechatUser);


        WechatStudentUserBO except = studentRepository.findWetChatUser(new StudentAccount("2017025838"));

        assertThat(actual).isEqualTo(except);
        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(2);
    }

    @Test
    public void testConcurrentBindDiffOpenId() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();

        StudentAccount studentAccount = new StudentAccount("2014025838");

        WechatStudentUserBO before = studentRepository.findWetChatUser(studentAccount);
        WechatStudentUserBO after = studentRepository.findWetChatUser(studentAccount);
        studentBindApp.bindByOpenId(before, new WechatUser("test_appId", "test_openid_before"));
        assertThatThrownBy(() -> studentBindApp.bindByOpenId(after, new WechatUser("test_appId", "test_openid_after")))
                .hasCauseInstanceOf(DuplicateKeyException.class);

    }

    @Test
    @Ignore
    public void testConcurrentBindDiffAccount() {

        truncateMapper.wechatOpenId();
        truncateMapper.wechat_action_record();

        WechatStudentUserBO before = studentBindApp.getStudentUserBO(new StudentAccount("2014025838"), "1");
        WechatStudentUserBO after = studentBindApp.getStudentUserBO(new StudentAccount("2017025838"), "1");

        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");
        studentBindApp.bindByOpenId(before, wechatUser);
        studentBindApp.bindByOpenId(after, wechatUser);

        assertThat(wechatActionRecordRepository.find(wechatUser).size()).isEqualTo(2);

    }
}