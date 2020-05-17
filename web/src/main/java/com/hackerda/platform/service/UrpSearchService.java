package com.hackerda.platform.service;

import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.CourseTimetable;
import com.hackerda.platform.pojo.Teacher;
import com.hackerda.platform.pojo.UrpClass;
import com.hackerda.platform.spider.newmodel.searchclass.ClassInfoSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.CourseTimetableSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.SearchClassInfoPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomResult;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrpSearchService {
    private NewUrpSpiderService newUrpSpiderService;

    @Autowired
    public void setNewUrpSpiderService(NewUrpSpiderService newUrpSpiderService){
        this.newUrpSpiderService = newUrpSpiderService;
    }

    public List<CourseTimetableSearchResult> searchTimetableByCourse(Course course) {
        for (List<CourseTimetableSearchResult> resultList : newUrpSpiderService.searchCourseTimeTable(course)) {
            return resultList;
        }
        return Collections.emptyList();
    }

    public List<CourseTimetableSearchResult> searchTeacherCourseTimetable(Teacher teacher) {
        for (List<CourseTimetableSearchResult> resultList :
                newUrpSpiderService.searchCourseTimetableByTeacher(teacher.getAccount())) {
            return resultList;
        }
        return Collections.emptyList();
    }


    public List<SearchClassroomResult> searchUrpClassroom(SearchClassroomPost searchClassroomPost) {
        for (SearchResultWrapper<SearchClassroomResult> resultWrapper : newUrpSpiderService.searchClassroomInfo(searchClassroomPost)) {
            return resultWrapper.getPageData().getRecords();
        }
        return Collections.emptyList();
    }

    public List<SearchClassroomResult> searchAllUrpClassroom() {
        SearchClassroomPost post = new SearchClassroomPost();
        post.setExecutiveEducationPlanNum("2019-2020-1-1");
        return searchUrpClassroom(post);
    }


    public List<UrpClass> searchUrpClass(SearchClassInfoPost searchClassInfoPost) {
        return newUrpSpiderService.getClassInfoSearchResult(searchClassInfoPost).stream()
                .flatMap(x -> x.getRecords().stream())
                .map(ClassInfoSearchResult::transToUrpClass)
                .collect(Collectors.toList());
    }

    public List<CourseTimetable> searchCourse(String termYear, int termOrder, String classNum) {
        return newUrpSpiderService.searchClassTimeTable(termYear, termOrder, classNum)
                .stream().flatMap(Collection::stream)
                .map(CourseTimetableSearchResult::transToCourseTimetable)
                .flatMap(Collection::stream)
                .peek(x -> x.setGmtCreate(new Date()))
                .collect(Collectors.toList());

    }

}
