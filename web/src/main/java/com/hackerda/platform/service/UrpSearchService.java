package com.hackerda.platform.service;

import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.Teacher;
import com.hackerda.platform.pojo.UrpClass;
import com.hackerda.platform.spider.newmodel.searchclass.ClassInfoSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.SearchClassInfoPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomResult;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchResultWrapper;
import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrpSearchService {
    private NewUrpSpiderService newUrpSpiderService;

    @Autowired
    public void setNewUrpSpiderService(NewUrpSpiderService newUrpSpiderService){
        this.newUrpSpiderService = newUrpSpiderService;
    }









    public List<UrpClass> searchUrpClass(SearchClassInfoPost searchClassInfoPost) {
        return newUrpSpiderService.getClassInfoSearchResult(searchClassInfoPost).stream()
                .flatMap(x -> x.getRecords().stream())
                .map(ClassInfoSearchResult::transToUrpClass)
                .collect(Collectors.toList());
    }

}
