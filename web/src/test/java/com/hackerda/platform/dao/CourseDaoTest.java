package com.hackerda.platform.dao;

import com.hackerda.platform.pojo.Course;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@Slf4j
@ActiveProfiles("beta")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseDaoTest {

    @Autowired
    private CourseDao courseDao;

    @Test
    public void selectByCourseList() {

        ArrayList<Course> list = Lists.newArrayList(
                new Course().setNum("1102009").setCourseOrder("01").setTermYear("2019-2020").setTermOrder(1),
                new Course().setNum("1102057").setCourseOrder("01").setTermYear("2019-2020").setTermOrder(1)
        );
        List<Course> courses = courseDao.selectByCourseList(list);

        System.out.println(courses);
    }

    @Test
    public void insertSelective() {
    }
}