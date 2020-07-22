package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.mapper.ext.TruncateMapper;
import com.hackerda.platform.repository.student.StudentUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class CourseTimetableRepositoryImplTest {
    @Autowired
    private CourseTimetableRepository courseTimetableRepository;
    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private TruncateMapper truncateMapper;

    @Test
    public void getCurrentTermTable() {
        truncateMapper.course();
        truncateMapper.classCourseTimetable();
        truncateMapper.courseTimetable();
        truncateMapper.studentCourseTimetable();

        StudentUserBO account = studentUserRepository.getByAccount(2017025838);

        CourseTimeTableOverview account1 = courseTimetableRepository.getByAccount(account, "2019-2020", 2);
        courseTimetableRepository.saveByStudent(account1.getNewList(), account);

        assertThat(account1.isFetchSuccess()).isTrue();

        CourseTimeTableOverview account2 = courseTimetableRepository.getByAccount(account, "2019-2020", 2);

        assertThat(account2.isFetchSuccess()).isFalse();
        assertThat(account2.getNewList().size() == 0).isTrue();

    }

    @Test
    public void getCurrentTermByClassId() {
    }

    @Test
    public void saveByStudent() {
    }

    @Test
    public void saveByClass() {
        truncateMapper.course();
        truncateMapper.classCourseTimetable();
        truncateMapper.courseTimetable();
        truncateMapper.studentCourseTimetable();

        StudentUserBO account = studentUserRepository.getByAccount(2017025838);

        CourseTimeTableOverview account1 = courseTimetableRepository.getByClassId(account, "2019-2020", 2);
        courseTimetableRepository.saveByClass(account1.getNewList(), account);

        assertThat(account1.isFetchSuccess()).isTrue();

        CourseTimeTableOverview account2 = courseTimetableRepository.getByClassId(account, "2019-2020", 2);

        assertThat(account2.isFetchSuccess()).isFalse();
        assertThat(account2.getNewList().size() == 0).isTrue();


    }
}