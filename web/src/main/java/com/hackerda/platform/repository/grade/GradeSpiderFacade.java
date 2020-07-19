package com.hackerda.platform.repository.grade;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.Grade;
import com.hackerda.platform.pojo.StudentUser;

import java.util.List;

public interface GradeSpiderFacade {

    List<Grade> getCurrentTermGrade(StudentUserBO student);

    List<Grade> getSchemeGrade(StudentUserBO student);
}
