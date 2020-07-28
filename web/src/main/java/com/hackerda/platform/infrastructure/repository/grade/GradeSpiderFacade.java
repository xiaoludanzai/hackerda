package com.hackerda.platform.infrastructure.repository.grade;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.Grade;

import java.util.List;

public interface GradeSpiderFacade {

    List<Grade> getCurrentTermGrade(StudentUserBO student);

    List<Grade> getSchemeGrade(StudentUserBO student);
}
