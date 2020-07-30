package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.model.CourseTimetableDetailDO;
import com.hackerda.platform.infrastructure.database.model.StudentCourseTimeTable;
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
public class CourseTimetableExtMapperTest {

    @Autowired
    private CourseTimetableExtMapper courseTimetableExtMapper;

    @Test
    public void selectDetailByStudentAccount() {
        StudentCourseTimeTable timeTable = new StudentCourseTimeTable();

        timeTable.setStudentId(2017025838).setTermYear("2019-2020").setTermOrder(1);

        List<CourseTimetableDetailDO> detailDOList = courseTimetableExtMapper.selectDetailByStudentAccount(timeTable);

        for (CourseTimetableDetailDO detailDO : detailDOList) {
            System.out.println(detailDO);
        }
    }
}