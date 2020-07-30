package com.hackerda.platform.infrastructure.database.mapper.ext;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class StudentUserExtMapperTest {

    @Autowired
    private StudentUserExtMapper studentUserExtMapper;

    @Test
    public void saveOrUpdate() {




    }
}