package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.application.CourseTimetableQueryApp;
import com.hackerda.platform.infrastructure.database.mapper.ext.TruncateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class CourseTimetableQueryAppTest {

    @Autowired
    private CourseTimetableQueryApp courseTimetableQueryApp;
    @Autowired
    private TruncateMapper truncateMapper;


    @Test
    public void getByAccount() {
        truncateMapper.course();
        truncateMapper.classCourseTimetable();
        truncateMapper.courseTimetable();
        truncateMapper.studentCourseTimetable();


        CourseTimeTableOverview account1 = courseTimetableQueryApp.getByAccount(2017025838, "2019-2020", 2);

        assertThat(account1.isFetchSuccess()).isTrue();

        CourseTimeTableOverview account2 = courseTimetableQueryApp.getByAccount(2017025838, "2019-2020", 2);

        assertThat(account2.isFetchSuccess()).isFalse();
        assertThat(account2.getNewList().size() == 0).isTrue();
    }
}