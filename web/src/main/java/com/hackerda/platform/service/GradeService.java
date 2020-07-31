package com.hackerda.platform.service;

import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.controller.vo.GradeResultVo;
import com.hackerda.platform.domain.student.StudentUserRepository;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeQueryApp gradeQueryApp;
    @Autowired
    private GradeTransfer gradeTransfer;
    @Autowired
    private StudentUserRepository studentUserRepository;


    public GradeResultVo getGrade() {
        String account =  SecurityUtils.getSubject().getPrincipal().toString();
        StudentUserBO studentUserBO = studentUserRepository.getByAccount(Integer.parseInt(account));
        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(studentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }
}
