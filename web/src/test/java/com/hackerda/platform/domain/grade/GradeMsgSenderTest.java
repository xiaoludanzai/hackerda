package com.hackerda.platform.domain.grade;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.student.StudentWechatBindDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@Slf4j
@ActiveProfiles("prod")
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeMsgSenderTest {

    @Autowired
    private GradeMsgSender gradeMsgSender;

    @Test
    public void sendUpdateGradeToStudent() {

        WechatStudentUserBO bo = new WechatStudentUserBO();

        bo.setName("test");

        StudentWechatBindDetail openidBO = new StudentWechatBindDetail(1231, "oCxRO1G9N755dOY5dwcT5l3IlS3Y", true, "wx541fd36e6b400648",
                WechatPlatform.HKXJ_PLUS, false);

        List<StudentWechatBindDetail> list = Collections.singletonList(openidBO);

        bo.setWechatOpenidList(list);


        GradeBO gradeBO = new GradeBO();

        gradeBO.setOperateTime("20200112142216");
        CourseBO courseBO = new CourseBO();
        courseBO.setName("测试课程");

        gradeBO.setScore(100.0);
        gradeBO.setRank(1000);
        gradeBO.setGradePoint(2.0);
        gradeBO.setCourse(courseBO);


        gradeMsgSender.sendUpdateGradeToStudent(bo, Collections.singletonList(gradeBO));
    }
}