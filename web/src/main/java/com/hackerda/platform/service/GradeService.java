package com.hackerda.platform.service;

import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.StudentRepository;
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
    private StudentRepository studentRepository;
    @Autowired
    private GradeTransfer gradeTransfer;


    public GradeResultVo getGrade() {
        StudentUserBO wechatStudentUserBO = (StudentUserBO) SecurityUtils.getSubject().getPrincipal();
        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(wechatStudentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }

    public GradeResultVo getGrade(int account) {
        StudentAccount studentAccount = new StudentAccount(account);
        WechatStudentUserBO wechatStudentUserBO = studentRepository.findWetChatUser(studentAccount);

        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(wechatStudentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }
}
