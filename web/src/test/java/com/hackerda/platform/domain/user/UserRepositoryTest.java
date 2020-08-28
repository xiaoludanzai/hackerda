package com.hackerda.platform.domain.user;

import com.hackerda.platform.domain.student.StudentAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@ActiveProfiles("beta")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByStudentAccount() {
        AppStudentUserBO appStudentUserBO = userRepository.findByStudentAccount(new StudentAccount("2014025838"));

        System.out.println(appStudentUserBO);
    }

    @Test
    public void findRoleByName() {
    }

    @Test
    public void store() {
        StudentAccount studentAccount = new StudentAccount("2014025828");
        PhoneNumber phoneNumber = new PhoneNumber("17301086159");
        AppStudentUserBO appStudentUserBO = new AppStudentUserBO(studentAccount, "test", "1", "test_avatarPath", phoneNumber, Gender.Man,
                "test_introdunction");

        userRepository.store(appStudentUserBO);


    }
}