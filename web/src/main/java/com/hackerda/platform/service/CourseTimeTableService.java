package com.hackerda.platform.service;

import com.google.common.collect.Lists;
import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.dao.*;
import com.hackerda.platform.domain.course.timetable.CourseTimetableQueryService;
import com.hackerda.platform.pojo.*;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;

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


    @Resource
    private StudentUserDao studentUserDao;



    public List<CourseTimeTableVo> getCurrentTermCourseTimeTableByStudent(int account) {
        StudentUser user = studentUserDao.selectStudentByAccount(account);
        if(user == null){
            throw new PasswordUnCorrectException();
        }
        return null;
    }

}
