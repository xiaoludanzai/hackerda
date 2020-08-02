package com.hackerda.platform.domain.grade;

import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.infrastructure.database.mapper.GradeMapper;
import com.hackerda.platform.infrastructure.repository.grade.GradeSpiderFacade;
import com.hackerda.platform.infrastructure.repository.student.StudentUserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("beta")
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeQueryAppTest {
    @Autowired
    private GradeQueryApp gradeQueryApp;

    @Autowired
    private GradeRepository repository;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private StudentUserRepositoryImpl studentUserRepository;
    @MockBean
    private GradeSpiderFacade gradeSpiderFacade;

    @Test
    public void getGrade() {

        gradeMapper.truncate();

        WechatStudentUserBO user = studentUserRepository.getWetChatUserByAccount(2017025838);

        GradeOverviewBO bo = gradeQueryApp.getGradeOverview(user);

        assertThat(bo.fetchSuccess());
        assertThat(bo.getUpdateGrade()).isEmpty();

        GradeBO update = bo.getNewGrade().stream().findAny().orElse(null);

        assert update != null;
        update.setScore(-2.0);
        repository.update(Collections.singletonList(update));

        GradeOverviewBO bo2 = gradeQueryApp.getGradeOverview(user);

        assertThat(bo.fetchSuccess());
        List<GradeBO> updateGrade = bo2.getUpdateGrade();
        assertThat(updateGrade.size()).isEqualTo(1);

    }

    @Test
    public void testFetchSuccess(){

        when(gradeSpiderFacade.getCurrentTermGrade(any())).thenReturn(Collections.emptyList());
        when(gradeSpiderFacade.getSchemeGrade(any())).thenThrow(new RuntimeException("fetch error"));

        WechatStudentUserBO user = studentUserRepository.getWetChatUserByAccount(2017025838);

        GradeOverviewBO bo = gradeQueryApp.getGradeOverview(user);

        assertThat(bo.fetchSuccess()).isFalse();


    }
}