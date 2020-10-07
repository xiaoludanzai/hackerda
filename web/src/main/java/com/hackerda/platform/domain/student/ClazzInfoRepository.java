package com.hackerda.platform.domain.student;

import com.hackerda.platform.infrastructure.database.model.UrpClass;

import java.util.List;

public interface ClazzInfoRepository {


    List<AcademyBO> findAcademy(String grade);

    List<SubjectBO> findSubject(String grade, String academyNum);

    List<ClazzBO> findClazz(String grade, String academyNum, String subjectNum);

    UrpClass findClassByNum(String classNum);
}
