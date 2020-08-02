package com.hackerda.platform.application;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.grade.GradeOverviewFactory;
import com.hackerda.platform.domain.grade.GradeRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.application.event.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeQueryApp {

    @Autowired
    private GradeOverviewFactory factory;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private EventPublisher eventPublisher;


    public GradeOverviewBO getGradeOverview(WechatStudentUserBO studentUser) {

        GradeOverviewBO gradeOverviewBO = factory.create(studentUser);

        List<GradeBO> updateGrade = gradeOverviewBO.getUpdateGrade();
        gradeRepository.update(updateGrade);

        List<GradeBO> newGrade = gradeOverviewBO.getNewGrade();
        gradeRepository.save(newGrade);

        if(gradeOverviewBO.isEverGradeFinishFetch()){
            eventPublisher.publishGradeFetchFinish(studentUser.getAccount().toString());
        }

        return gradeOverviewBO;

    }


}
