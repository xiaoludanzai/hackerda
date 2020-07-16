package com.hackerda.platform.domain.grade;

import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeQueryServiceTest {
    @Autowired
    private GradeQueryService gradeQueryService;
    @Autowired
    private StudentUserDao userDao;


    @Test
    public void getGrade() {

        StudentUser user = userDao.selectStudentByAccount(2017025838);

        GradeResultVo grade = gradeQueryService.getGrade(user);


        System.out.println(grade);
    }
}