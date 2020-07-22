package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.CourseTimetableDetailDO;
import com.hackerda.platform.repository.student.StudentUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class CourseTimetableSpiderFacadeTest {

    @Autowired
    private CourseTimetableSpiderFacade courseTimetableSpiderFacade;
    @Autowired
    private StudentUserRepository studentUserRepository;

    @Test
    public void test(){

        StudentUserBO byAccount = studentUserRepository.getByAccount(2017025838);

        List<CourseTimetableDetailDO> id = courseTimetableSpiderFacade.getByClassID("2019-2020", 2, byAccount.getUrpClassNum().toString());


    }
}