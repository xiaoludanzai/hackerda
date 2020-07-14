package com.hackerda.platform.domain.grade;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GradeOverviewBO {

    private List<TermGradeBO> termGradeList;

    private double gpa;

    private int gpaRank;

    private int gpaRankSize;

    private double optionalCourseCredit;

    public GradeOverviewBO (List<TermGradeBO> termGradeList) {
        this.termGradeList = termGradeList;

        double sumGradePoint = 0.0;
        double sumCredit = 0.0;
        double sumOptionalCredit = 0.0;

        List<GradeBO> collect = termGradeList.stream()
                .flatMap(x -> x.getGradeList().stream())
                .filter(GradeBO::hasScore)
                .collect(Collectors.toList());

        // count gpa
        for (GradeBO grade : collect) {
            double credit = grade.getCredit();
            sumCredit += credit;
            sumGradePoint += grade.getCreditGradePoint();
            if(grade.isOptional()){
                sumOptionalCredit += credit;
            }
        }
        this.setOptionalCourseCredit(sumOptionalCredit);


        if(sumCredit != 0){
            double f = sumGradePoint / sumCredit;
            BigDecimal b = new BigDecimal(f);
            this.setGpa(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }


    public List<GradeBO> getUpdateGrade(){
        if(!CollectionUtils.isEmpty(termGradeList)) {
            return termGradeList.get(0).getGradeList()
                    .stream()
                    .filter(GradeBO::isUpdate)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<GradeBO> getCurrentTermNewGrade(){
        return Collections.emptyList();
    }

    public List<GradeBO> getGradeNeedToSave(){
        return Collections.emptyList();
    }
}
