package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.constant.SubscribeScene;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@ActiveProfiles("prod")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentUserRepositoryTest {
    @Autowired
    private StudentUserRepositoryImpl studentUserRepository;

    @Test
    public void getByAccount() {
        StudentUserBO account = studentUserRepository.getByAccount(2017024981);
        System.out.println(account);

    }

    @Test
    public void getByAccountList() {
    }

    @Test
    public void getSubscribe() {

        List<WechatStudentUserBO> userBOList = studentUserRepository.getSubscribe(SubscribeScene.GRADE_AUTO_UPDATE);
        System.out.println(userBOList.size());
    }
}