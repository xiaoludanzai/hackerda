package com.hackerda.platform.repository;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.grade.GradeRepository;
import com.hackerda.platform.domain.grade.TermGradeBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.mapper.GradeMapper;
import com.hackerda.platform.repository.student.StudentUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeRepositoryImplTest {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private StudentUserRepository studentUserRepository;



    @Test
    public void testUpdate(){
        gradeMapper.truncate();

        StudentUserBO user = studentUserRepository.getByAccount(2017025838);

        List<TermGradeBO> termGradeBOList = gradeRepository.getAllByStudent(user);

        GradeBO gradeBO = termGradeBOList.get(0).getGradeList().stream().findAny().orElse(null);

        if(gradeBO != null){
            gradeBO.setScore(-2.0);
            gradeRepository.update(Collections.singletonList(gradeBO));
        }

        List<TermGradeBO> termGradeBOList2 = gradeRepository.getAllByStudent(user);
        List<GradeBO> update = termGradeBOList2.get(0).getUpdate();

        assert update.size() == 1;
    }


    @Test
    public void testNewGrade(){

        StudentUserBO user = studentUserRepository.getByAccount(2017025838);

        List<TermGradeBO> termGradeBOList = gradeRepository.getAllByStudent(user);

        termGradeBOList.get(0).getGradeList().stream().findAny()
                .ifPresent(gradeBO -> gradeRepository.delete(gradeBO));

        List<TermGradeBO> termGradeBOList2 = gradeRepository.getAllByStudent(user);
        List<GradeBO> newGrade = termGradeBOList2.get(0).getNew();

        assert newGrade.size() == 1;
    }

    @Test
    public void testGetGrade(){

        StudentUserBO user = studentUserRepository.getByAccount(2017025838);

        List<TermGradeBO> termGradeBOList = gradeRepository.getAllByStudent(user);


    }



}