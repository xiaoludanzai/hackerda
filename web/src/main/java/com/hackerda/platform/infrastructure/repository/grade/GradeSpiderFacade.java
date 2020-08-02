package com.hackerda.platform.infrastructure.repository.grade;

import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.infrastructure.database.model.Grade;

import java.util.List;

public interface GradeSpiderFacade {

    List<Grade> getCurrentTermGrade(WechatStudentUserBO student);

    List<Grade> getSchemeGrade(WechatStudentUserBO student);
}
