package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.repository.student.StudentUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("prod")
@SpringBootTest
public class CourseTimetableRepositoryImplTest {
    @Autowired
    private CourseTimetableRepository courseTimetableRepository;
    @Autowired
    private StudentUserRepository studentUserRepository;

    @Test
    public void getCurrentTermTable() {
        StudentUserBO account = studentUserRepository.getByAccount(2017025838);
        CourseTimeTableOverview currentTermTable = courseTimetableRepository.getByAccount(account, "2019-2020", 1);

        System.out.println(currentTermTable);

    }

    @Test
    public void getCurrentTermByClassId() {
    }

    @Test
    public void saveByStudent() {
    }

    @Test
    public void saveByClass() {
    }
}