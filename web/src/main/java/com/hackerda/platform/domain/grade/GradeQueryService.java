package com.hackerda.platform.domain.grade;

import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeQueryService {

    @Autowired
    private GradeOverviewFactory factory;
    @Autowired
    private GradeRepository gradeRepository;


    public GradeResultVo getGrade(StudentUser studentUser) {

        GradeOverviewBO gradeOverviewBO = factory.create(studentUser);

        List<GradeBO> updateGrade = gradeOverviewBO.getUpdateGrade();

        gradeRepository.update(updateGrade);

        gradeRepository.save(gradeOverviewBO.getGradeNeedToSave());

        return null;

    }

}
