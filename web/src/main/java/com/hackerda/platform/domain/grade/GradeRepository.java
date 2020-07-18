package com.hackerda.platform.domain.grade;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.StudentUser;

import java.util.List;

public interface GradeRepository {

    void save(List<GradeBO> gradeList);

    void update(List<GradeBO> gradeList);

    void delete(GradeBO grade);

    List<TermGradeBO> getAllByStudent(StudentUserBO studentUser);
}
