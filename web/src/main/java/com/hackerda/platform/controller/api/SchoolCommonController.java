package com.hackerda.platform.controller.api;


import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.controller.vo.CourseTimetableOverviewVO;
import com.hackerda.platform.controller.vo.GradeResultVo;
import com.hackerda.platform.service.CourseTimeTableService;
import com.hackerda.platform.service.GradeService;
import com.hackerda.platform.service.rbac.UserAuthorizeService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SchoolCommonController {

    @Autowired
    private CourseTimeTableService courseTimeTableService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private UserAuthorizeService userAuthorizeService;

    @RequiresAuthentication
    @RequestMapping(value = "/grade")
    public WebResponse getNowGradeV2() {

        GradeResultVo grade = gradeService.getGrade();

        if (grade.getErrorCode() == ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode()){
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号或密码错误");
        }

        return WebResponse.success(grade);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/timetable")
    public WebResponse getTimeTableV2() {

        CourseTimetableOverviewVO vo = courseTimeTableService.getCurrentTermCourseTimeTableByStudent();
        if (vo.getErrorCode() == ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode()){
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号或密码错误");
        }
        return WebResponse.success(vo.getCourseTimetableVOList());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/unbind")
    public WebResponse appUnbind(@RequestParam(value = "account", required = false) String account,
                                 @RequestParam(value = "appId") String appId) {

        userAuthorizeService.appStudentRevokeAuthorize(account, appId);
        return WebResponse.success("success");
    }
}
