package com.hackerda.platform.domain.grade;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.mapper.GradeMapper;
import com.hackerda.platform.repository.grade.GradeSpiderFacade;
import com.hackerda.platform.repository.student.StudentUserRepository;
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
public class GradeQueryServiceTest {
    @Autowired
    private GradeQueryService gradeQueryService;

    @Autowired
    private GradeRepository repository;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private StudentUserRepository studentUserRepository;
    @MockBean
    private GradeSpiderFacade gradeSpiderFacade;

    @Test
    public void getGrade() {

        gradeMapper.truncate();

        StudentUserBO user = studentUserRepository.getByAccount(2017025838);

        GradeOverviewBO bo = gradeQueryService.getGradeOverview(user);

        assertThat(bo.fetchSuccess());
        assertThat(bo.getUpdateGrade()).isEmpty();

        GradeBO update = bo.getNewGrade().stream().findAny().orElse(null);

        assert update != null;
        update.setScore(-2.0);
        repository.update(Collections.singletonList(update));

        GradeOverviewBO bo2 = gradeQueryService.getGradeOverview(user);

        assertThat(bo.fetchSuccess());
        List<GradeBO> updateGrade = bo2.getUpdateGrade();
        assertThat(updateGrade.size()).isEqualTo(1);

    }

    @Test
    public void testFetchSuccess(){

        when(gradeSpiderFacade.getCurrentTermGrade(any())).thenReturn(Collections.emptyList());
        when(gradeSpiderFacade.getSchemeGrade(any())).thenThrow(new RuntimeException("fetch error"));

        StudentUserBO user = studentUserRepository.getByAccount(2017025838);

        GradeOverviewBO bo = gradeQueryService.getGradeOverview(user);

        assertThat(bo.fetchSuccess()).isFalse();


    }
}