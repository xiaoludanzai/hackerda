package com.hackerda.platform.infrastructure.database.dao.user;

import com.hackerda.platform.infrastructure.database.model.StudentPosterDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void selectByStudentPoster() {
        StudentPosterDO studentPosterDO = userDao.selectByStudentPoster("2014025838");
        System.out.println(studentPosterDO);
    }
}