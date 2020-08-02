package com.hackerda.platform.service;

import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.controller.vo.GradeResultVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeQueryApp gradeQueryApp;
    @Autowired
    private GradeTransfer gradeTransfer;


    public GradeResultVo getGrade() {
        WechatStudentUserBO wechatStudentUserBO = (WechatStudentUserBO) SecurityUtils.getSubject().getPrincipal();
        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(wechatStudentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }
}
