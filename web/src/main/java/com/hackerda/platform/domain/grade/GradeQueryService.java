package com.hackerda.platform.domain.grade;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.platform.repository.student.StudentUserRepository;
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
    @Autowired
    private StudentUserRepository studentUserRepository;


    public GradeResultVo getGrade(StudentUserBO studentUser) {

        GradeOverviewBO gradeOverviewBO = getOverview(studentUser);

        return gradeTransfer.adapter2VO(gradeOverviewBO);

    }

    public GradeResultVo getGrade(int account) {
        StudentUserBO studentUserBO = studentUserRepository.getByAccount(account);
        GradeOverviewBO gradeOverviewBO = getOverview(studentUserBO);
        return gradeTransfer.adapter2VO(gradeOverviewBO);

    }

    private GradeOverviewBO getOverview(StudentUserBO studentUser) {
        GradeOverviewBO gradeOverviewBO = factory.create(studentUser);

        if(gradeOverviewBO.fetchSuccess()){
            List<GradeBO> updateGrade = gradeOverviewBO.getUpdateGrade();
            gradeRepository.update(updateGrade);

            List<GradeBO> newGrade = gradeOverviewBO.getNewGrade();
            gradeRepository.save(newGrade);

        }
        return gradeOverviewBO;
    }

    public GradeOverviewBO getGradeOverview(StudentUserBO studentUser) {

        return getOverview(studentUser);

    }


}
