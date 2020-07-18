package com.hackerda.platform.pojo.vo;

import com.hackerda.platform.pojo.Grade;
import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.Term;
import com.hackerda.platform.service.GpaRanker;
import com.hackerda.platform.utils.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class GradeResultVo {

    private List<TermGradeVo> termGradeList;

    private double gpa;

    private int gpaRank;

    private int gpaRankSize;

    private double optionalCourseCredit;
    /**
     * 这个errorCode 是针对抓取失败时的错误信息给前端提示，譬如超时或者未评估等等
     */
    private int errorCode;

    private String message;

    public GradeResultVo() {


    }

    public GradeResultVo(List<Grade> gradeList){
        List<TermGradeVo> termGradeVoList = gradeToTermGradeList(gradeList);
        this.termGradeList = termGradeVoList;

        double sumGradePoint = 0.0;
        double sumCredit = 0.0;
        double sumOptionalCredit = 0.0;

        List<GradeVo> collect = termGradeVoList.stream()
                .flatMap(x -> x.getGradeList().stream())
                .filter(x-> x.getScore() != -1)
                .collect(Collectors.toList());

        // count gpa
        for (GradeVo gradeVo : collect) {
            String creditStr = gradeVo.getCourse().getCredit();
            if(StringUtils.isNotEmpty(creditStr)){
                double credit = Double.parseDouble(creditStr);
                sumCredit += credit;
                sumGradePoint += (gradeVo.getGradePoint() * credit);
                if(gradeVo.getCoursePropertyCode().equals("003")){
                    sumOptionalCredit += credit;
                }
            }
        }

        if(sumCredit != 0){
            double f = sumGradePoint / sumCredit;
            BigDecimal b = new BigDecimal(f);

            this.setGpa(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            this.setOptionalCourseCredit(sumOptionalCredit);
        }

    }


    private List<TermGradeVo> gradeToTermGradeList(List<Grade> gradeList) {
        SchoolTime schoolTime = DateUtils.getCurrentSchoolTime();

        return gradeList.stream()
                .collect(Collectors.groupingBy(x -> new Term(x.getTermYear(), x.getTermOrder())))
                .entrySet().stream()
                .map(x -> new TermGradeVo()
                        .setTermYear(x.getKey().getTermYear())
                        .setTermOrder(x.getKey().getOrder())
                        .setGradeList(gradeToVoAndSort(x.getValue()))
                        .setCurrentTerm(schoolTime.getTerm().equals(x.getKey()))
                )
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<GradeVo> gradeToVoAndSort(List<Grade> gradeList){
        return gradeList.stream()
                .map(Grade::toVo)
                .sorted()
                .collect(Collectors.toList());
    }



}
