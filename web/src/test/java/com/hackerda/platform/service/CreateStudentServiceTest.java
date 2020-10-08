package com.hackerda.platform.service;

import com.hackerda.platform.controller.request.CreateStudentRequest;
import com.hackerda.platform.controller.vo.CourseTimetableOverviewVO;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class CreateStudentServiceTest {

    @Autowired
    private CreateStudentService createStudentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseTimeTableService courseTimeTableService;
    @Autowired
    private GradeService gradeService;


    @Test
    public void createStudentUser() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setAccount("2020025838");
        request.setName("测试");
        request.setClazzNum("2020150003");
        request.setAppId("test_appId");
        request.setOpenid("test_openId");
        request.setGender("男");

        createStudentService.createStudentUser(request);

        StudentUserBO userBO = studentRepository.find(new StudentAccount("2020025838"));
        assertThat(userBO.isMsgHasCheck()).isFalse();

        CreateStudentRequest request2 = new CreateStudentRequest();
        request2.setAccount("2020025838");
        request2.setName("测试2");
        request2.setClazzNum("2020150003");
        request2.setAppId("test_appId");
        request2.setOpenid("test_openId");
        request2.setGender("男");

        createStudentService.createStudentUser(request2);

        WechatStudentUserBO userBO2 = studentRepository.findWetChatUser(new StudentAccount("2020025838"));
        assertThat(userBO2.isMsgHasCheck()).isFalse();
        assertThat(userBO2.getName()).isEqualTo("测试2");

        assertThat(userBO2.hasBindWechatUser(new WechatUser("test_appId", "test_openId"))).isTrue();


        CreateStudentRequest request3 = new CreateStudentRequest();
        request3.setAccount("2020025838");
        request3.setName("测试2");
        request3.setClazzNum("2020150003");
        request3.setAppId("test_appId");
        request3.setOpenid("test_openId2");
        request3.setGender("男");

        assertThatThrownBy(() ->createStudentService.createStudentUser(request3))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ACCOUNT_HAS_BIND);
    }

    @Test
    public void testStudentApi() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setAccount("2020025838");
        request.setName("测试");
        request.setClazzNum("2020150003");
        request.setAppId("test_appId");
        request.setOpenid("test_openId");
        request.setGender("男");

        createStudentService.createStudentUser(request);

        CourseTimetableOverviewVO table = courseTimeTableService.getCurrentTermCourseTimeTableByStudent(2020025838);

        gradeService.getGrade(2020025838);

    }

}