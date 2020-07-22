package com.hackerda.platform.service;

import com.hackerda.platform.mapper.TeacherClassMapper;
import com.hackerda.platform.mapper.TeacherCourseMapper;
import com.hackerda.platform.mapper.TeacherMapper;
import com.hackerda.platform.pojo.Teacher;
import com.hackerda.platform.pojo.TeacherClass;
import com.hackerda.platform.pojo.TeacherCourse;
import com.hackerda.platform.pojo.constant.Academy;
import com.hackerda.platform.spider.newmodel.SearchResult;

import com.hackerda.platform.spider.newmodel.searchteacher.SearchTeacherPost;
import com.hackerda.platform.spider.newmodel.searchteacher.SearchTeacherResult;
import com.google.common.base.Splitter;
import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeacherService {
    @Resource
    private NewUrpSpiderService newUrpSpiderService;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private TeacherClassMapper teacherClassMapper;
    @Resource
    private TeacherCourseMapper teacherCourseMapper;


    public void parseTeacherData(){
        SearchTeacherPost post = new SearchTeacherPost();
        post.setExecutiveEducationPlanNum("2019-2020-1-1");

        for (SearchResult<SearchTeacherResult> result : newUrpSpiderService.searchTeacherInfo( post)) {
            for (SearchTeacherResult record : result.getRecords()) {
                Teacher teacher = spiderDataToPojo(record);
                saveTeacherDate(teacher);
            }
        }
    }

    @Transactional
    public void saveTeacherDate(Teacher teacher){
        Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();


        HashSet<TeacherClass> classNumSet = new HashSet<>();
        HashSet<TeacherCourse> courseIdSet = new HashSet<>();

        for (List<CourseTimetableSearchResult> searchResults : newUrpSpiderService.searchCourseTimetableByTeacher(teacher.getAccount())) {
            for (CourseTimetableSearchResult result : searchResults) {

                String termName = result.getTermName();
                String termYear = termName.substring(0, 9);
                int termOrder = getTermOrder(termName);
                if(result.getClassIdList() == null){
                    continue;
                }

                List<TeacherClass> teacherClassList = splitter.splitToList(result.getClassIdList()).stream()
                        .map(x -> new TeacherClass()
                                .setClassId(Integer.parseInt(x))
                                .setTeacherId(teacher.getAccount())
                                .setTermOrder(termOrder)
                                .setTermYear(termYear))
                        .collect(Collectors.toList());

                classNumSet.addAll(teacherClassList);
                String courseId = result.getId().getCourseId();

                courseIdSet.add(new TeacherCourse()
                        .setTeacherId(teacher.getAccount())
                        .setTermOrder(termOrder)
                        .setTermYear(termYear)
                        .setCourseId(courseId));

            }
        }
        teacherMapper.insertSelective(teacher);
        for (TeacherClass teacherClass : classNumSet) {
            teacherClassMapper.insertSelective(teacherClass);
        }
        for (TeacherCourse teacherCourse : courseIdSet) {
            teacherCourseMapper.insertSelective(teacherCourse);
        }

    }

    private int getTermOrder(String termName){
        if(termName.contains("一")){
            return 1;
        }else {
            return 2;
        }

    }

    private Teacher spiderDataToPojo(SearchTeacherResult record){

        Academy academy = Academy.getAcademyByUrpCode(Integer.parseInt(record.getDepartmentNum()));

        return
                new Teacher().setAccount(record.getId().getTeacherNumber())
                .setSex(record.getSex())
                .setName(record.getTeacherName())
                .setAcademy(academy.getAcademyCode())
                .setProfessionalTitle(record.getProfessionalTitle());
    }
}
