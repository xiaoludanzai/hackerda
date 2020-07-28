package com.hackerda.platform.service;

import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.vo.GradeResultVo;
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
        StudentUserBO studentUserBO = (StudentUserBO) SecurityUtils.getSubject().getPrincipal();
        GradeOverviewBO gradeOverview = gradeQueryApp.getGradeOverview(studentUserBO);
        return gradeTransfer.adapter2VO(gradeOverview);

    }
}
