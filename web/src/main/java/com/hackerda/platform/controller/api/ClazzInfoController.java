package com.hackerda.platform.controller.api;


import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.controller.vo.AppUserVO;
import com.hackerda.platform.controller.vo.student.AcademyVO;
import com.hackerda.platform.controller.vo.student.ClazzInfoVO;
import com.hackerda.platform.controller.vo.student.ClazzVO;
import com.hackerda.platform.controller.vo.student.SubjectVO;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.service.CreateStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clazzInfo")
public class ClazzInfoController {

    @Autowired
    private CreateStudentService studentService;


    @GetMapping("/getAcademy")
    public WebResponse<ClazzInfoVO<AcademyVO>> getAcademy(@RequestParam(value = "grade") String grade) {

        return WebResponse.success(studentService.getAcademyByGrade(grade));
    }

    @GetMapping("/getSubject")
    public WebResponse<ClazzInfoVO<SubjectVO>> getSubject(@RequestParam(value = "grade") String grade,
                                                          @RequestParam(value = "academyNum") String academyNum) {

        return WebResponse.success(studentService.getSubject(grade, academyNum));
    }

    @GetMapping("/getClazz")
    public WebResponse<ClazzInfoVO<ClazzVO>> getClazz(@RequestParam(value = "grade") String grade,
                                                      @RequestParam(value = "academyNum") String academyNum,
                                                      @RequestParam(value = "subjectNum") String subjectNum
                                    ) {

        return WebResponse.success(studentService.getClazz(grade, academyNum, subjectNum));
    }
}
