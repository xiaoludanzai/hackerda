package com.hackerda.platform.service;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.pojo.Exam;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.spider.UrpSpider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTimeTableServiceTest {

    @Autowired
    private CourseTimeTableService courseTimeTableService;
    @Autowired
    NewGradeSearchService newGradeSearchService;
    @Autowired
    private ExamTimeTableService examTimeTableService;
    @Autowired
    private StudentUserDao studentUserDao;
    @Autowired
    private NewUrpSpiderService newUrpSpiderService;


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
    public void check(){

        for(int x= 0 ;x< 3; x++){

            UrpSpider baseSpider = newUrpSpiderService.getBaseSpider();
            System.out.println(baseSpider);
        }

    }



    @Test
    public void getExamFromSpider(){
        List<Exam> examList = examTimeTableService.getExamTimeList(2017025838);
        System.out.println(examList);
    }

    @Test
    public void runba() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(4);
        List<StudentUser> users = studentUserDao.selectAllStudent()
                .stream()
                .filter(x-> x.getAccount().toString().startsWith("2017"))
                .filter(StudentUser::getIsCorrect)
                .collect(Collectors.toList());
        CountDownLatch latch = new CountDownLatch(users.size());
        for (StudentUser studentUser : users) {
            service.submit(() ->{
                try {
                    GradeResultVo grade = newGradeSearchService.getGrade(studentUser.getAccount().toString());
                    System.out.println(studentUser.getAccount().toString() + " "+ grade.getMessage());
                }catch (Exception e){
                    log.error("{}", studentUser.getAccount().toString(), e);
                }finally {
                    latch.countDown();
                }

            });
        }
        latch.await();
    }

    @Test
    public void runba1() {


        List<StudentUser> users = studentUserDao.selectAllStudent()
                .stream()
                .filter(x-> x.getAccount().toString().startsWith("2017"))
                .filter(x -> x.getSubjectName().equals("会计学"))
                .collect(Collectors.toList());
        CountDownLatch latch = new CountDownLatch(users.size());
        for (StudentUser studentUser : users) {
            try {
                GradeResultVo grade = newGradeSearchService.getGrade(studentUser.getAccount().toString());
                System.out.println(studentUser.getAccount().toString() + " "+ grade.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
        }
    }
}