package com.hackerda.platform.repository;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.pojo.Grade;
import com.hackerda.platform.pojo.StudentUser;

import java.util.List;

public interface GradeSpiderFacade {

    List<Grade> getCurrentTermGrade(StudentUser student);

    List<Grade> getSchemeGrade(StudentUser student);
}
