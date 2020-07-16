package com.hackerda.platform.service;

import com.hackerda.platform.dao.CourseDao;
import com.hackerda.platform.dao.UrpCourseDao;
import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.UrpCourse;
import com.hackerda.platform.spider.newmodel.SearchResult;
import com.hackerda.platform.spider.newmodel.course.UrpCourseForSpider;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCoursePost;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCourseResult;
import com.hackerda.platform.utils.DateUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Yuki
 * @date 2019/9/2 15:56
 */
@Slf4j
@Service
public class UrpCourseService {


    @Resource
    private CourseDao courseDao;

    @Resource
    private NewUrpSpiderService newUrpSpiderService;


    private static final Cache<String, Course> currentTermCourseCache = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .build();


    public Course getCurrentTermCourse(String courseId, String sequenceNumber) {
        return getCurrentTermCourse(courseId, sequenceNumber, null);

    }

    public Course getCurrentTermCourse(String courseId, String sequenceNumber, Course updateCourse) {
        SchoolTime schoolTime = DateUtils.getCurrentSchoolTime();
        String termYear = schoolTime.getTerm().getTermYear();
        int order = schoolTime.getTerm().getOrder();
        return getCourseFromCache(courseId, sequenceNumber,termYear, order, updateCourse);
    }

    public Course getCourseFromCache(String courseId, String sequenceNumber, String termYear, int termOrder){
        return getCourseFromCache(courseId, sequenceNumber, termYear, termOrder, null);
    }


    public Course getCourseFromCache(String courseId, String sequenceNumber, String termYear, int termOrder, Course updateCourse){
        String key = courseId + sequenceNumber + termYear + termOrder;
        try {
            return currentTermCourseCache.get(key, () -> {
                Course course = getCourse(courseId, sequenceNumber, termYear,
                        termOrder, updateCourse);

                if (course == null) {
                    log.info(" {} {} {} {}", courseId, sequenceNumber, termYear,
                            termOrder);
                }
                return course;
            });
        } catch (ExecutionException e) {
            log.error("get course cache error key {}", key, e);
            throw new RuntimeException(e);
        }

    }


    /**
     * @param courseId
     * @param sequenceNumber
     * @param termYear
     * @param termOrder
     * @param updateCourse   这个传入的course主要作用是课程查询的时候有比较多缺省的值，从该对象中获取
     * @return
     */
    public Course getCourse(String courseId, String sequenceNumber, String termYear, int termOrder, Course updateCourse) {
        List<Course> courseList = courseDao.selectCourseByPojo(
                new Course()
                        .setNum(courseId)
                        .setCourseOrder(sequenceNumber)
                        .setTermYear(termYear)
                        .setTermOrder(termOrder));

        if (courseList.size() == 0) {
            SearchCoursePost post = new SearchCoursePost();
            post.setCourseNumber(courseId).setCourseOrderNumber(sequenceNumber);
            post.setExecutiveEducationPlanNum(termYear + "-" + termOrder + "-1");
            SearchResult<SearchCourseResult> searchResult = newUrpSpiderService.searchCourseInfo(post);
            if (CollectionUtils.isEmpty(searchResult.getRecords())) {
                courseDao.insertSelective(updateCourse);
                return updateCourse;
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
                throw new RuntimeException("search course result more than one");
            }

            Course course = resultList.get(0).transToCourse();
            if (updateCourse != null) {
                course.setCredit(updateCourse.getCredit());
                course.setExamType(updateCourse.getExamType());
                course.setExamTypeCode(updateCourse.getExamTypeCode());
                course.setCourseOrder(updateCourse.getCourseOrder());
                course.setTeacherAccount(course.getTeacherAccount() == null ? "" : course.getTeacherAccount());
            }
            courseDao.insertSelective(course);
            return course;
        }
        return courseList.stream().findFirst().get();
    }

}
