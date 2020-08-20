package com.hackerda.platform.infrastructure.repository.course.timetable;

import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.infrastructure.database.model.CourseTimetableDetailDO;
import com.hackerda.platform.infrastructure.repository.student.StudentUserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("prod")
@SpringBootTest
public class CourseTimetableSpiderFacadeTest {

    @Autowired
    private CourseTimetableSpiderFacade courseTimetableSpiderFacade;
    @Autowired
    private StudentUserRepositoryImpl studentUserRepository;

    @Test
    public void test(){

        WechatStudentUserBO byAccount = studentUserRepository.getWetChatUserByAccount(2017026003);

        List<CourseTimetableDetailDO> id = courseTimetableSpiderFacade.getByClassID("2020-2021", 1,
                byAccount.getUrpClassNum().toString());
        for (CourseTimetableDetailDO detailDO : id) {
            System.out.println(detailDO);
        }


    }
}