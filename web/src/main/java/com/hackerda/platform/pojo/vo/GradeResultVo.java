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




}
