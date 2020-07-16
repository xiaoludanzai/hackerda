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
    @Autowired
    private GradeTransfer gradeTransfer;


    public GradeResultVo getGrade(StudentUser studentUser) {

        GradeOverviewBO gradeOverviewBO = getOverview(studentUser);

        return gradeTransfer.adapter2VO(gradeOverviewBO);

    }

    private GradeOverviewBO getOverview(StudentUser studentUser) {
        GradeOverviewBO gradeOverviewBO = factory.create(studentUser);

        if(gradeOverviewBO.fetchSuccess()){
            List<GradeBO> updateGrade = gradeOverviewBO.getUpdateGrade();
            gradeRepository.update(updateGrade);

            List<GradeBO> newGrade = gradeOverviewBO.getNewGrade();
            gradeRepository.save(newGrade);

        }
        return gradeOverviewBO;
    }

    public GradeOverviewBO getGradeOverview(StudentUser studentUser) {

        return getOverview(studentUser);

    }


}
