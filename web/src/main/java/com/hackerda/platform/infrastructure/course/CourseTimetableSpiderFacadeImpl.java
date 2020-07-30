package com.hackerda.platform.infrastructure.course;

import com.google.common.collect.Lists;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.infrastructure.database.model.CourseTimetableDetailDO;
import com.hackerda.platform.infrastructure.repository.course.timetable.CourseTimetableSpiderFacade;
import com.hackerda.platform.service.NewUrpSpiderService;
import com.hackerda.spider.UrpSearchSpider;
import com.hackerda.spider.support.coursetimetable.CourseTimetableSearchResult;
import com.hackerda.spider.support.coursetimetable.TimeAndPlace;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseTimetableSpiderFacadeImpl implements CourseTimetableSpiderFacade {

    @Autowired
    private NewUrpSpiderService newUrpSpiderService;
    @Autowired
    private UrpSearchSpider urpSearchSpider;

    @Override
    public List<CourseTimetableDetailDO> getCurrentTermTableByAccount(StudentUserBO studentUserBO) {

        UrpCourseTimeTable timeTable = newUrpSpiderService.getUrpCourseTimeTable(studentUserBO);


        return timeTable.getDetails()
                .stream().flatMap(x -> x.values().stream()
                        .map(course -> {
                                    List<CourseTimetableDetailDO> result = Lists.newArrayList();
                                    String[] termYearAndTermOrder =
                                            parseTermYearAndTermOrder(course.getCourseRelativeInfo().getExecutiveEducationPlanNumber());
                                    for (TimeAndPlace timeAndPlace : course.getTimeAndPlaceList()) {
                                        for (int[] weekArray : TimeAndPlace.parseWeek(timeAndPlace.getWeekDescription())) {
                                            result.add(
                                                    new CourseTimetableDetailDO()
                                                            .setTermYear(termYearAndTermOrder[0])
                                                            .setTermOrder(Integer.parseInt(termYearAndTermOrder[1]))
                                                            .setCourseId(course.getCourseRelativeInfo().getCourseNumber())
                                                            .setCourseSequenceNumber(timeAndPlace.getCourseSequenceNumber())
                                                            .setStartWeek(weekArray[0])
                                                            .setEndWeek(weekArray[1])
                                                            .setWeekDescription(timeAndPlace.getWeekDescription())
                                                            .setClassOrder(timeAndPlace.getClassSessions())
                                                            .setClassDay(timeAndPlace.getClassDay())
                                                            .setAttendClassTeacher(course.getAttendClassTeacher())
                                                            .setContinuingSession(timeAndPlace.getContinuingSession())
                                                            .setCampusName(timeAndPlace.getCampusName())
                                                            .setRoomName(timeAndPlace.getClassroomName())
                                                            .setClassInSchoolWeek(timeAndPlace.getClassWeek())
                                                            .setCourseName(course.getCourseName())
                                                            .setTeacherName(course.getAttendClassTeacher())
                                                            .setCredit(course.getUnit().toString())
                                                            .setCourseTypeCode(course.getCourseCategoryCode())
                                                            .setCourseType(course.getCourseCategoryName())
                                                            .setExamType(course.getExamTypeName())
                                                            .setExamTypeCode(course.getExamTypeCode())
                                                            .setRoomName(timeAndPlace.getClassroomName())
                                                            .setCampusName(timeAndPlace.getCampusName())
                                                            .setContinuingSession(timeAndPlace.getContinuingSession())
                                            );
                                        }
                                    }
                                    return result;
                                }

                        )).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseTimetableDetailDO> getByClassID(String termYear, int termOrder, String classId) {
        List<List<CourseTimetableSearchResult>> list = urpSearchSpider.searchClassTimeTable(termYear, termOrder, classId);
        return list.stream().flatMap(Collection::stream)
                .map(this::transToCourseTimetable)
                .flatMap(Collection::stream)
                .peek(x -> x.setGmtCreate(new Date()))
                .collect(Collectors.toList());
    }


    public List<CourseTimetableDetailDO> transToCourseTimetable(CourseTimetableSearchResult result) {
        return TimeAndPlace.parseWeek(result.getWeekDescription()).stream().map(x ->
                new CourseTimetableDetailDO()
                        .setCourseId(result.getId().getCourseId())
                        .setCourseSequenceNumber(result.getId().getCourseOrderNumber())
                        .setAttendClassTeacher(result.getTeacherName())
                        .setCampusName(result.getCampusName())
                        .setClassDay(result.getId().getClassWeek())
                        .setClassOrder(result.getId().getClassOrderInWeek())
                        .setStudentCount(result.getStudentCount())
                        .setContinuingSession(result.getContinuingSession())
                        .setRoomNumber(result.getClassRoomNumber())
                        .setRoomName(result.getClassRoomName())
                        .setClassInSchoolWeek(result.getId().getClassInSchoolWeek())
                        .setTermYear(result.getTermYear())
                        .setTermOrder(result.getTermOrder())
                        .setWeekDescription(result.getWeekDescription())
                        .setStartWeek(x[0])
                        .setEndWeek(x[1])
                        .setClassDistinct(TimeAndPlace.parseDistinct(result.getId().getClassInSchoolWeek(), x[0], x[1]))
                        .setCourseName(result.getCourseName())
                        .setCredit(result.getCredit())
                        .setExamType(result.getExamTypeName())
                        .setExamTypeCode(result.getExamTypeNumber())
        )

                .collect(Collectors.toList());


    }

    private String[] parseTermYearAndTermOrder(String executiveEducationPlanNumber) {
        String[] results = executiveEducationPlanNumber.split("-");
        return new String[]{results[0] + "-" + results[1], results[2]};
    }
}
