package com.hackerda.platform.infrastructure.course;

import com.hackerda.platform.infrastructure.database.model.Course;
import com.hackerda.platform.infrastructure.repository.course.CourseSpiderFacade;


import com.hackerda.platform.domain.constant.Academy;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.spider.UrpSearchSpider;
import com.hackerda.spider.support.search.SearchResult;
import com.hackerda.spider.support.search.course.SearchCoursePost;
import com.hackerda.spider.support.search.course.SearchCourseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseSpiderFacadeImpl implements CourseSpiderFacade {

    @Resource
    private UrpSearchSpider urpSearchSpider;


    @Override
    public Course get(String courseId, String sequenceNumber, String termYear, int termOrder) {
        SearchCoursePost post = new SearchCoursePost();
        post.setCourseNumber(courseId).setCourseOrderNumber(sequenceNumber);
        post.setExecutiveEducationPlanNum(termYear + "-" + termOrder + "-1");
        urpSearchSpider.searchCourseInfo(post);
        SearchResult<SearchCourseResult> searchResult = urpSearchSpider.searchCourseInfo(post);
        if (CollectionUtils.isEmpty(searchResult.getRecords())) {
            return null;
        }

        List<SearchCourseResult> resultList = searchResult.getRecords();

        if (resultList.size() > 1) {
            resultList = resultList.stream()
                    .filter(x -> post.getCourseNumber().equals(x.getCourseId()))
                    .collect(Collectors.toList());
            if (resultList.size() != 1) {
                log.error("search course result more than one. post {} records {}", post, searchResult.getRecords());
                throw new RuntimeException("search course result more than one");
            }

        } else if (resultList.size() == 0) {
            log.error("search course result empty. post {}", post);
            return null;
        }


        return transToCourse(resultList.get(0));
    }


    public Course transToCourse(SearchCourseResult result) {
        return new Course()
                .setName(result.getCourseName())
                .setNum(result.getCourseId())
                .setCourseOrder(StringUtils.isEmpty(result.getCourseOrder()) ? "01" : result.getCourseOrder())
                .setCredit(result.getCredit())
                .setAcademyCode(result.getAcademyCode())
                .setAcademyName(getAcademyName(result))
                .setTeacherName(result.getTeacherNameList())
                .setTeacherAccount(result.getTermNumber())
                .setCourseType(result.getCourseTypeName())
                .setCourseTypeCode(result.getCourseTypeCode())
                .setExamType(result.getExamTypeName())
                .setExamTypeCode(result.getExamTypeCode())
                .setTermYear(getTermYear(result.getTermYear()))
                .setTermOrder(getTermOrder(result.getTermName()));
    }

    private int getTermOrder(String termName) {
        if (StringUtils.isEmpty(termName)) {
            return DateUtils.getCurrentSchoolTime().getTerm().getOrder();
        }

        if (termName.contains("一")) {
            return 1;
        } else {
            return 2;
        }

    }

    private String getAcademyName(SearchCourseResult result){
        if (StringUtils.isEmpty(result.getAcademyName()) && !StringUtils.isEmpty(result.getAcademyCode())){
            return Academy.getAcademyByUrpCode(Integer.parseInt(result.getAcademyCode())).getAcademyName();
        }else{
            return "";
        }
    }

    public String getTermYear(String termName) {
        // 这里是个补偿做法 由于有些课程没有该数据  所以存储当前的学期
        if (StringUtils.isEmpty(termName)) {
            return DateUtils.getCurrentSchoolTime().getTerm().getTermYear();
        }
        return termName.substring(0, 9);
    }
}
