package com.hackerda.platform.service;

import com.hackerda.platform.domain.user.LogoutType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void logout() {
        userService.logout("admin","2014025838" ,LogoutType.TEST_USER.getCode(), LogoutType.TEST_USER.getSource());
    }
}