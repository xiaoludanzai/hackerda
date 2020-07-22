package com.hackerda.platform.controller;


import com.hackerda.platform.pojo.Exam;
import com.hackerda.platform.pojo.WebResponse;
import com.hackerda.platform.pojo.constant.ErrorCode;
import com.hackerda.platform.service.ExamTimeTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/timetable")
public class TimetableController {

    @Resource
    private ExamTimeTableService examTimeTableService;


    @GetMapping("/teacher")
    public WebResponse getTimeTableByTeacher(@RequestParam(value = "account") String account) {
        if (Objects.isNull(account)) {
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号无效");
        }

        return WebResponse.success(Collections.emptyList());
    }

    @GetMapping("/class")
    public WebResponse getTimeTableByClassNum(@RequestParam(value = "classNum") String classNum) {
        if (Objects.isNull(classNum)) {
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号无效");
        }

        return WebResponse.success(Collections.emptyList());
    }

    @GetMapping("/classroom")
    public WebResponse getTimeTableByClassroom(@RequestParam(value = "roomId") String roomId) {
        if (Objects.isNull(roomId)) {
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "教室号不能为空");
        }

        return WebResponse.success(Collections.emptyList());
    }

    @GetMapping("/course")
    public WebResponse getTimeTableByCourse(@RequestParam(value = "courseId") String courseId,
                                            @RequestParam(value = "courseOrder") String courseOrder) {

        return WebResponse.success(Collections.emptyList());
    }

    @GetMapping("/search")
    public WebResponse searchTimeTable(@RequestParam(value = "q") String query,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "size", required = false) Integer size
    ) {

        if (page == null || size == null) {
            page = 0;
            size = 10;
        }

        return WebResponse.success(Collections.emptyList());
    }

    @GetMapping("/exam")
    public WebResponse getExamTimeTableByStudent(@RequestParam(value = "account") String account) {
        if (Objects.isNull(account)) {
            return WebResponse.fail(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID.getErrorCode(), "账号无效");
        }

        List<Exam> examTimeList = examTimeTableService.getExamTimeList(Integer.parseInt(account));
        return WebResponse.success(examTimeList);
    }


}
