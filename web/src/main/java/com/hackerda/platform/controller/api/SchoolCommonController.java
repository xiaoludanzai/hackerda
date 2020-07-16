package com.hackerda.platform.controller.api;


import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.domain.grade.GradeQueryService;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.WebResponse;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.platform.service.CourseTimeTableService;
import com.hackerda.platform.service.NewGradeSearchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolCommonController {

    @Resource
    private CourseTimeTableService courseTimeTableService;
    @Autowired
    private GradeQueryService gradeQueryService;
    @Autowired
    private StudentUserDao studentUserDao;

    @RequiresAuthentication
    @RequestMapping(value = "/grade")
    public WebResponse getNowGradeV2() {
        String account = SecurityUtils.getSubject().getPrincipal().toString();
        StudentUser student = studentUserDao.selectStudentByAccount(Integer.parseInt(account));
        GradeResultVo grade = gradeQueryService.getGrade(student);
        return WebResponse.success(grade);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/timetable")
    public WebResponse getTimeTableV2() {

        String account = SecurityUtils.getSubject().getPrincipal().toString();
        List<CourseTimeTableVo> courseTimeTableVoList = courseTimeTableService.getCurrentTermCourseTimeTableByStudent(Integer.parseInt(account));
        return WebResponse.success(courseTimeTableVoList);
    }
}
