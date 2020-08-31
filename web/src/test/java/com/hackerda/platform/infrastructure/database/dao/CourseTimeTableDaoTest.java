package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.model.ClassCourseTimetable;
import com.hackerda.platform.infrastructure.database.model.CourseTimetableDetailDO;
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
@ActiveProfiles("prod")
@SpringBootTest
public class CourseTimeTableDaoTest {

    @Autowired
    private CourseTimeTableDao courseTimeTableDao;

    @Test
    public void selectDetailByClassId() {

        ClassCourseTimetable relative = new ClassCourseTimetable()
                .setClassId("2017120008")
                .setTermYear("2020-2021")
                .setTermOrder(1);

        long start = System.currentTimeMillis();

        List<CourseTimetableDetailDO> timetableList = courseTimeTableDao.selectDetailByClassId(relative);

        System.out.println(System.currentTimeMillis() - start);

        System.out.println(timetableList.size());
    }
}