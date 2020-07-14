package com.hackerda.platform.domain.grade;

import com.hackerda.platform.pojo.StudentUser;

import java.util.List;

public interface GradeRepository {

    void save(List<GradeBO> gradeList);

    void update(List<GradeBO> gradeList);

    List<TermGradeBO> getAllByStudent(StudentUser studentUser);
}
