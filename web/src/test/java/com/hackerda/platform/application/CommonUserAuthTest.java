package com.hackerda.platform.application;


import com.hackerda.platform.domain.student.StudentInfoAssist;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonUserAuthTest {

    @MockBean
    private StudentInfoAssist studentInfoAssist;


    @Test
    public void test() {
        boolean commonWechat = studentInfoAssist.isCommonWechat(null, null);
        System.out.println(commonWechat);
    }

}
