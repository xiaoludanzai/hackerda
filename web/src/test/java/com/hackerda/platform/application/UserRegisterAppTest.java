package com.hackerda.platform.application;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.AppUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.domain.user.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("beta")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegisterAppTest {

    @Autowired
    private UserRegisterApp userRegisterApp;

    @Test
    public void register() {
        StudentAccount studentAccount = new StudentAccount("2017025838");
        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman,
                "test_introduction");

        userRegisterApp.register(appStudentUserBO);

        AppUserBO account = userRegisterApp.getUserByStudentAccount(studentAccount);

        assertThat(account).isEqualTo(appStudentUserBO);

    }

    @Test
    public void getUserByStudentAccount() {
        StudentAccount studentAccount = new StudentAccount("2014025838");
        AppUserBO account = userRegisterApp.getUserByStudentAccount(studentAccount);

        System.out.println(account);
    }
}