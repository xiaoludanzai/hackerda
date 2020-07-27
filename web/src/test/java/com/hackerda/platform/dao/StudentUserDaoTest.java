package com.hackerda.platform.dao;

import com.hackerda.platform.pojo.StudentUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class StudentUserDaoTest {

    @Autowired
    private StudentUserDao studentUserDao;

    @Test
    public void saveOrUpdate() {

        StudentUser studentUser = studentUserDao.selectStudentByAccount(2014025838);

        studentUser.setPassword("1");

        studentUserDao.saveOrUpdate(studentUser);

        StudentUser studentUser1 = studentUserDao.selectStudentByAccount(2014025838);

        assert studentUser1.getPassword().equals("1");


        BeanCopier beanCopier = BeanCopier.create(StudentUser.class, StudentUser.class, false);

        StudentUser copy = new StudentUser();
        beanCopier.copy(studentUser, copy, null);

        copy.setAccount(156879431);

        studentUserDao.saveOrUpdate(copy);

        StudentUser studentUser2 = studentUserDao.selectStudentByAccount(156879431);

        assert studentUser2 != null;

    }
}