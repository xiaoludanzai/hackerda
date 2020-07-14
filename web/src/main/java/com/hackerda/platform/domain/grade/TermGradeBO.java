package com.hackerda.platform.domain.grade;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TermGradeBO implements Comparable<TermGradeBO> {
    private List<GradeBO> gradeList;

    private String termYear;

    private int termOrder;

    private boolean isCurrentTerm;

    private boolean fetchSuccess;

    private boolean needToFetch;

    private String errorMsg;

    private int errorCode;

    public String getExecutiveEducationPlanNum() {
        return this.getTermYear() + "-" + this.getTermOrder()+ "-1";
    }

    @Override
    public int compareTo(TermGradeBO o) {
        return this.getExecutiveEducationPlanNum().compareTo(o.getExecutiveEducationPlanNum());
    }
}
