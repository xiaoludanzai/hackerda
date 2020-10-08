package com.hackerda.platform.infrastructure.spider;

import com.hackerda.platform.infrastructure.database.model.UrpClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class StudentInfoServiceImplScript {

    @Autowired
    private StudentInfoServiceImpl studentInfoService;

    @Test
    public void getClassByName() {

        UrpClass classByName = studentInfoService.getClassByName("", "2020025838");
    }
}