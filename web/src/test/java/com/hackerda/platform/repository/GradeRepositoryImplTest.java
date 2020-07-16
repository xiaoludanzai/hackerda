package com.hackerda.platform.repository;

import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.grade.GradeRepository;
import com.hackerda.platform.domain.grade.TermGradeBO;
import com.hackerda.platform.mapper.GradeMapper;
import com.hackerda.platform.pojo.StudentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeRepositoryImplTest {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentUserDao userDao;
    @Autowired
    private GradeMapper gradeMapper;


    @Before
    public void init(){

    }


    @Test
    public void testUpdate(){


        gradeMapper.truncate();

        StudentUser user = userDao.selectStudentByAccount(2017025838);

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

        StudentUser user = userDao.selectStudentByAccount(2017025838);

        List<TermGradeBO> termGradeBOList = gradeRepository.getAllByStudent(user);

        termGradeBOList.get(0).getGradeList().stream().findAny()
                .ifPresent(gradeBO -> gradeRepository.delete(gradeBO));

        List<TermGradeBO> termGradeBOList2 = gradeRepository.getAllByStudent(user);
        List<GradeBO> newGrade = termGradeBOList2.get(0).getNew();

        assert newGrade.size() == 1;


    }

}