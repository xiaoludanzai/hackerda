package com.hackerda.platform.infrastructure.course;

import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.infrastructure.repository.course.CourseSpiderFacade;
import com.hackerda.platform.service.NewUrpSpiderService;
import com.hackerda.platform.spider.newmodel.SearchResult;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCoursePost;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCourseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseSpiderFacadeImpl implements CourseSpiderFacade {

    @Resource
    private NewUrpSpiderService newUrpSpiderService;


    @Override
    public Course get(String courseId, String sequenceNumber, String termYear, int termOrder) {
        SearchCoursePost post = new SearchCoursePost();
        post.setCourseNumber(courseId).setCourseOrderNumber(sequenceNumber);
        post.setExecutiveEducationPlanNum(termYear + "-" + termOrder + "-1");
        SearchResult<SearchCourseResult> searchResult = newUrpSpiderService.searchCourseInfo(post);
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


        return resultList.get(0).transToCourse();
    }
}
