package com.hackerda.platform.service;

import com.hackerda.platform.pojo.Exam;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTimeTableServiceTest {

    @Autowired
    private CourseTimeTableService courseTimeTableService;
    @Autowired
    NewGradeSearchService newGradeSearchService;
    @Autowired
    private ExamTimeTableService examTimeTableService;


    @Test
    public void getCurrentTermCourseTimeTableByStudent() {

        List<CourseTimeTableVo> table = courseTimeTableService.getCurrentTermCourseTimeTableByStudent(2017025838);

        System.out.println(table);
    }


    @Test
    public void getGradeFromSpider(){
        GradeResultVo grade = newGradeSearchService.getGrade("2017025838");
        System.out.println(grade);
    }



    @Test
    public void getExamFromSpider(){
        List<Exam> examList = examTimeTableService.getExamTimeList(2017025838);
        System.out.println(examList);
    }
}