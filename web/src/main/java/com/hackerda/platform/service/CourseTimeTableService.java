package com.hackerda.platform.service;

import com.google.common.collect.Lists;
import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.dao.*;
import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableQueryService;
import com.hackerda.platform.pojo.*;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;

import com.hackerda.platform.pojo.vo.CourseTimetableOverviewVO;
import com.hackerda.platform.pojo.vo.CourseVO;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpRequestException;
import com.hackerda.spider.support.coursetimetable.TimeAndPlace;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Yuki
 * @date 2019/8/29 21:40
 */
@Slf4j
@Service
public class CourseTimeTableService {

    @Autowired
    private CourseTimetableQueryService courseTimetableQueryService;


    public CourseTimetableOverviewVO getCurrentTermCourseTimeTableByStudent(int account) {

        Term term = DateUtils.getCurrentSchoolTime().getTerm();

        CourseTimeTableOverview timeTableOverview = courseTimetableQueryService.getByAccount(account, term.getTermYear(), term.getOrder());

        return toVO(timeTableOverview);
    }

    public CourseTimetableOverviewVO toVO(CourseTimeTableOverview timeTableOverview){

        CourseTimetableOverviewVO vo = new CourseTimetableOverviewVO();
        vo.setErrorCode(timeTableOverview.getErrorCode());
        vo.setErrorMsg(timeTableOverview.getErrorMsg());

        List<CourseTimeTableVo> collect = timeTableOverview.getCourseTimetableBOList().stream().map(x -> {

            CourseTimeTableVo tableVo = new CourseTimeTableVo();

            tableVo.setRoomName(x.getRoomName());
            tableVo.setRoomNumber(x.getRoomNumber());

            tableVo.setAttendClassTeacher(x.getAttendClassTeacher());
            tableVo.setCampusName(x.getCampusName());
            tableVo.setClassDay(x.getClassDay());
            tableVo.setClassOrder(x.getClassOrder());
            tableVo.setClassInSchoolWeek(x.getClassInSchoolWeek());
            tableVo.setContinuingSession(x.getContinuingSession());
            tableVo.setEndWeek(x.getEndWeek());
            tableVo.setStartWeek(x.getStartWeek());

            tableVo.setTermOrder(x.getTermOrder());
            tableVo.setTermYear(x.getTermYear());
            tableVo.setWeekDescription(x.getWeekDescription());
            tableVo.setStudentCount(x.getStudentCount());

            CourseVO courseVO = new CourseVO();

            courseVO.setNum(x.getCourseBO().getNum());
            courseVO.setCourseOrder(x.getCourseBO().getCourseOrder());
            courseVO.setCredit(x.getCourseBO().getCredit());
            courseVO.setName(x.getCourseBO().getName());

            tableVo.setCourse(courseVO);

            return tableVo;
        }).collect(Collectors.toList());

        vo.setCourseTimetableVOList(collect);

        return vo;

    }

}
