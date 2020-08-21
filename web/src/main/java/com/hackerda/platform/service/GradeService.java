package com.hackerda.platform.service;

import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
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
    private StudentUserRepository studentUserRepository;
    @Autowired
    private GradeTransfer gradeTransfer;


    public GradeResultVo getGrade() {
        StudentUserBO wechatStudentUserBO = (StudentUserBO) SecurityUtils.getSubject().getPrincipal();
        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(wechatStudentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }

    public GradeResultVo getGrade(int account) {
        WechatStudentUserBO wechatStudentUserBO = studentUserRepository.getWetChatUserByAccount(account);
        String enablePassword = wechatStudentUserBO.getEnablePassword();
        System.out.println(enablePassword);
        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(wechatStudentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }
}
