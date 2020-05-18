package com.hackerda.platform.service;

import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTimeTableServiceTest {

    @Autowired
    private CourseTimeTableService courseTimeTableService;

    @Test
    public void getCurrentTermCourseTimeTableByStudent() {

        List<CourseTimeTableVo> table = courseTimeTableService.getCurrentTermCourseTimeTableByStudent(2017025838);

        System.out.println(table);
    }
}