package com.hackerda.platform.domain.grade;

import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.service.GpaRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeOverviewFactory {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GpaRanker gpaRanker;


    public GradeOverviewBO create(StudentUser student) {


        List<TermGradeBO> allByStudent = gradeRepository.getAllByStudent(student);

        GradeOverviewBO bo = new GradeOverviewBO(allByStudent);

        GpaRanker.RankResult rankResult = gpaRanker.rank(student, bo.getGpa());

        bo.setGpaRank(rankResult.getRank());
        bo.setGpaRankSize(rankResult.getSize());

        return bo;
    }






}
