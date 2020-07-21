package com.hackerda.platform.service.course;

import com.google.common.collect.Lists;
import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.CourseTimetable;
import com.hackerda.platform.pojo.Term;
import com.hackerda.platform.repository.course.timetable.CourseTimetableSpiderFacade;
import com.hackerda.platform.service.NewUrpSpiderService;
import com.hackerda.platform.service.UrpSearchService;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.spider.support.coursetimetable.TimeAndPlace;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseTimetableSpiderFacadeImpl implements CourseTimetableSpiderFacade {

    @Autowired
    private NewUrpSpiderService newUrpSpiderService;
    @Autowired
    private UrpSearchService urpSearchService;

    @Override
    public List<CourseTimetable> getCurrentTermTableByAccount(StudentUserBO studentUserBO) {

        UrpCourseTimeTable timeTable = newUrpSpiderService.getUrpCourseTimeTable(studentUserBO);


        return timeTable.getDetails()
                .stream().flatMap(x -> x.values().stream()
                        .map(course -> {
                                    List<CourseTimetable> result = Lists.newArrayList();
                                    String[] termYearAndTermOrder =
                                            parseTermYearAndTermOrder(course.getCourseRelativeInfo().getExecutiveEducationPlanNumber());
                                    for (TimeAndPlace timeAndPlace : course.getTimeAndPlaceList()) {
                                        for (int[] weekArray : TimeAndPlace.parseWeek(timeAndPlace.getWeekDescription())) {
                                            result.add(
                                                    new CourseTimetable()
                                                            .setTermYear(termYearAndTermOrder[0])
                                                            .setTermOrder(Integer.parseInt(termYearAndTermOrder[1]))
                                                            .setCourseId(timeAndPlace.getCourseNumber())
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
                                                            .setClassInSchoolWeek(timeAndPlace.getClassWeek()));
                                        }
                                    }
                                    return result;
                                }

                        )).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseTimetable> getCurrentTermTableByClassID(StudentUserBO studentUserBO) {
        Term term = DateUtils.getCurrentSchoolTime().getTerm();
        return  urpSearchService.searchCourse(term.getTermYear(), term.getOrder(), studentUserBO.getUrpClassNum().toString());
    }

    private String[] parseTermYearAndTermOrder(String executiveEducationPlanNumber) {
        String[] results = executiveEducationPlanNumber.split("-");
        return new String[]{results[0] + "-" + results[1], results[2]};
    }
}
