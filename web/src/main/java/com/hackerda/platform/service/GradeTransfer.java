package com.hackerda.platform.service;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.grade.TermGradeBO;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.platform.pojo.vo.GradeVo;
import com.hackerda.platform.pojo.vo.TermGradeVo;
import com.hackerda.platform.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GradeTransfer {

    @Autowired
    private CourseTransfer courseTransfer;

    public GradeResultVo adapter2VO(GradeOverviewBO gradeOverviewBO){
        GradeResultVo vo = new GradeResultVo();

        vo.setGpa(gradeOverviewBO.getGpa());
        vo.setGpaRank(gradeOverviewBO.getGpaRank());
        vo.setGpaRankSize(gradeOverviewBO.getGpaRankSize());
        vo.setOptionalCourseCredit(gradeOverviewBO.getOptionalCourseCredit());

        vo.setTermGradeList(gradeOverviewBO.getTermGradeList().stream()
                .map(this::adapter2VO)
                .collect(Collectors.toList()));

        if(gradeOverviewBO.fetchSuccess()){
            vo.setErrorCode(gradeOverviewBO.getErrorCode());
            vo.setMessage(gradeOverviewBO.getErrorMsg());
        }

        return vo;
    }

    public TermGradeVo adapter2VO(TermGradeBO termGradeBO){
        TermGradeVo vo = new TermGradeVo();

        vo.setCurrentTerm(termGradeBO.isCurrentTerm());
        vo.setTermOrder(termGradeBO.getTermOrder());
        vo.setTermYear(termGradeBO.getTermYear());
        vo.setGradeList(termGradeBO.getGradeList().stream()
                .map(this::adapter2VO)
                .sorted()
                .collect(Collectors.toList()));

        return vo;
    }

    public GradeVo adapter2VO(GradeBO gradeBO){
        GradeVo vo = new GradeVo();
        vo.setCourse(courseTransfer.adapter2VO(gradeBO.getCourse()));
        vo.setScore(gradeBO.getScore());
        vo.setExamTime(DateUtils.localDateToDate(gradeBO.getExamTime(), DateUtils.YYYY_MM_DD_PATTERN));
        vo.setOperateTime(gradeBO.getOperateTime());

        vo.setCoursePropertyName(gradeBO.getCoursePropertyName());

        vo.setRank(gradeBO.getRank());
        vo.setTermOrder(gradeBO.getTermOrder());
        vo.setTermYear(gradeBO.getTermYear());
        vo.setGradePoint(gradeBO.getGradePoint());


        return vo;
    }
}
