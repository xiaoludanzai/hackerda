package com.hackerda.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import com.hackerda.spider.exception.UrpEvaluationException;
import com.hackerda.spider.exception.UrpSessionExpiredException;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import com.hackerda.spider.support.search.SearchResult;
import com.hackerda.spider.support.search.classInfo.ClassInfoSearchResult;
import com.hackerda.spider.support.search.classInfo.SearchClassInfoPost;
import com.hackerda.spider.support.search.classroom.SearchClassroomPost;
import com.hackerda.spider.support.search.classroom.SearchClassroomResult;
import com.hackerda.spider.support.search.classroom.SearchResultWrapper;
import com.hackerda.spider.support.search.course.SearchCoursePost;
import com.hackerda.spider.support.search.course.SearchCourseResult;
import com.hackerda.spider.support.search.emptyroom.EmptyRoomRecord;
import com.hackerda.spider.support.search.emptyroom.SearchEmptyRoomPost;
import com.hackerda.spider.support.search.teacher.SearchTeacherPost;
import com.hackerda.spider.support.search.teacher.SearchTeacherResult;
import okhttp3.FormBody;
import okhttp3.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class UrpSearchSpiderImpl extends UrpBaseSpider implements UrpSearchSpider{

    private final String SEARCH_COURSE_INFO = ROOT + "/student/teachingResources/classCurriculum" +
            "/searchCurriculumInfo/callback";

    /**
     * 查询课程对应的时间安排
     */
    private final String COURSE_TIMETABLE = ROOT + "/student/teachingResources/courseCurriculum" +
            "/searchCurriculum/callback?planCode=%s&courseCode=%s&courseSequenceCode=%s";


    private final String TEACHER_COURSE_TIME_TABLE = ROOT + "/student/teachingResources/teacherCurriculum" +
            "/searchCurriculumInfo/callback";

    /**
     * 班级信息查询
     */
    private final String CLASS_INFO = ROOT + "/student/teachingResources/classCurriculum/search";

    /**
     * 教师信息查询
     */
    private final String TEACHER_INFO = ROOT + "/student/teachingResources/teacherCurriculum/search";

    /**
     * 教室信息查询
     */
    private final String CLASSROOM_INFO = ROOT + "/student/teachingResources/classroomCurriculum/search";

    /**
     * 查询课程信息
     */
    private final String COURSE_SEARCH = ROOT + "/student/teachingResources/courseCurriculum/search";

    /**
     * 空教室查询
     */
    private  final String EMPTY_ROOM = ROOT + "/student/teachingResources/freeClassroomQuery/search";


    private static final TypeReference<List<List<CourseTimetableSearchResult>>> classCourseSearchResultReference
            = new TypeReference<List<List<CourseTimetableSearchResult>>>() {
    };

    public UrpSearchSpiderImpl(RestOperations client, CaptchaPredict captchaPredict,
                               ICaptchaProvider<CaptchaImage> captchaProvider) {
        super(client, captchaPredict, captchaProvider, new MemoryCookiePersist<>());
    }

    public UrpSearchSpiderImpl(RestOperations client, CaptchaPredict captchaPredict,
                               ICaptchaProvider<CaptchaImage> captchaProvider, AccountCookiePersist<String> cookiePersist) {
        super(client, captchaPredict, captchaProvider, cookiePersist);
    }


    private void login(){
        if(!hasLogin()){
            login("2014025838", "1");
        }

    }


    @Override
    public List<List<CourseTimetableSearchResult>> searchClassTimeTable(String termYear, int termOrder, String classCode) {
        login();
        String url = SEARCH_COURSE_INFO + "?planCode=%s-%s-1&classCode=%s";
        String format = String.format(url, termYear, termOrder, classCode);
        String content = getContent(format);

        return parseObject(content, classCourseSearchResultReference);
    }

    @Override
    public List<List<CourseTimetableSearchResult>> searchCourseTimetableByTeacher(String termYear, int termOrder, String teacherNumber) {
        String url = TEACHER_COURSE_TIME_TABLE + "?planCode="+termYear+"-"+termOrder+"-1&teacherNum=" + teacherNumber;
        String content = getContent(url);
        return parseObject(content, classCourseSearchResultReference);
    }

    @Override
    public List<List<CourseTimetableSearchResult>> searchCourseTimeTable(String termYear, int termOrder, String courseId, String courseOrder) {

        String url = String.format(COURSE_TIMETABLE, termYear+"-"+termOrder+"-1", courseId, courseOrder);

        String content = getContent(url);

        return parseObject(content, classCourseSearchResultReference);
    }

    @Override
    public List<SearchResult<ClassInfoSearchResult>> searchClassInfo(SearchClassInfoPost searchClassInfoPost) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("param_value", searchClassInfoPost.getParamValue());
        map.add("executiveEducationPlanNum", searchClassInfoPost.getExecutiveEducationPlanNum());
        map.add("pageNum", searchClassInfoPost.getPageNum());
        map.add("pageSize", searchClassInfoPost.getPageSize());
        map.add("yearNum", searchClassInfoPost.getYearNum());
        map.add("departmentNum", searchClassInfoPost.getDepartmentNum());
        map.add("classNum", searchClassInfoPost.getClassNum());


        ResponseEntity<String> entity = postFormData(map, CLASS_INFO,  String.class);

        TypeReference<List<SearchResult<ClassInfoSearchResult>>> reference = new TypeReference<List<SearchResult<ClassInfoSearchResult>>>() {
        };

        return parseObject(entity.getBody(), reference);

    }

    @Override
    public List<SearchResult<SearchTeacherResult>> searchTeacherInfo(SearchTeacherPost searchTeacherPost) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("executiveEducationPlanNumber", searchTeacherPost.getExecutiveEducationPlanNum());
        map.add("departmentNum", searchTeacherPost.getDepartmentNum());
        map.add("teacherName", searchTeacherPost.getTeacherName());
        map.add("pageSize", searchTeacherPost.getPageSize());
        map.add("pageNum", searchTeacherPost.getPageNum());


        ResponseEntity<String> entity = postFormData(map, TEACHER_INFO,  String.class);

        TypeReference<List<SearchResult<SearchTeacherResult>>> reference = new TypeReference<List<SearchResult<SearchTeacherResult>>>() {
        };
        return parseObject(entity.getBody(), reference);

    }

    @Override
    public List<SearchResultWrapper<SearchClassroomResult>> searchClassroomInfo(SearchClassroomPost searchClassroomPost) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("executiveEducationPlanNumber", searchClassroomPost.getExecutiveEducationPlanNum());
        map.add("campusNumber", searchClassroomPost.getCampusNumber());
        map.add("teachingBuildingNumber", searchClassroomPost.getTeachingBuildingNumber());
        map.add("classRoomNumber", searchClassroomPost.getClassRoomNumber());
        map.add("pageSize", searchClassroomPost.getPageSize());
        map.add("pageNum", searchClassroomPost.getPageNum());


        ResponseEntity<String> entity = postFormData(map, CLASSROOM_INFO,  String.class);

        TypeReference<List<SearchResultWrapper<SearchClassroomResult>>> reference = new TypeReference<List<SearchResultWrapper<SearchClassroomResult>>>() {
        };
        return parseObject(entity.getBody(), reference);

    }

    @Override
    public SearchResult<SearchCourseResult> searchCourseInfo(SearchCoursePost searchCoursePost) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("zxjxjhh", searchCoursePost.getExecutiveEducationPlanNum());
        map.add("kkxsh", searchCoursePost.getAcademyCode());
        map.add("kcm", searchCoursePost.getCourseName());
        map.add("kch", searchCoursePost.getCourseNumber());
        map.add("kclb", searchCoursePost.getCourseType());

        map.add("pageSize", searchCoursePost.getPageSize());
        map.add("pageNum", searchCoursePost.getPageNum());

        TypeReference<SearchResult<SearchCourseResult>> reference = new TypeReference<SearchResult<SearchCourseResult>>() {
        };

        ResponseEntity<String> entity = postFormData(map, COURSE_SEARCH,  String.class);

        return parseObject(entity.getBody(), reference);
    }

    @Override
    public List<SearchResultWrapper<EmptyRoomRecord>> searchEmptyRoom(SearchEmptyRoomPost searchEmptyRoomPost) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("weeks", searchEmptyRoomPost.getWeeks());
        map.add("executiveEducationPlanNumber", searchEmptyRoomPost.getExecutiveEducationPlanNumber());
        map.add("codeCampusListNumber", "01");
        map.add("teaNum", searchEmptyRoomPost.getTeaNum());

        map.add("wSection", searchEmptyRoomPost.getWSection());


        map.add("pageSize", searchEmptyRoomPost.getPageSize());
        map.add("pageNum", searchEmptyRoomPost.getPageNum());


        TypeReference<List<SearchResultWrapper<EmptyRoomRecord>>> reference = new TypeReference<List<SearchResultWrapper<EmptyRoomRecord>>>() {
        };

        ResponseEntity<String> entity = postFormData(map, EMPTY_ROOM,  String.class);

        return parseObject(entity.getBody(), reference);
    }
}
