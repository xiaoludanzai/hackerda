package com.hackerda.platform.controller.api;


import com.hackerda.platform.domain.grade.GradeQueryService;
import com.hackerda.platform.pojo.WebResponse;
import com.hackerda.platform.pojo.constant.ErrorCode;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.platform.service.CourseTimeTableService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolCommonController {

    @Resource
    private CourseTimeTableService courseTimeTableService;
    @Autowired
    private GradeQueryService gradeQueryService;

    @RequiresAuthentication
    @RequestMapping(value = "/grade")
    public WebResponse getNowGradeV2() {
        String account = SecurityUtils.getSubject().getPrincipal().toString();

        GradeResultVo grade = gradeQueryService.getGrade(Integer.parseInt(account));

        if (grade.getErrorCode() == ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode()){
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号或密码错误");
        }

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
