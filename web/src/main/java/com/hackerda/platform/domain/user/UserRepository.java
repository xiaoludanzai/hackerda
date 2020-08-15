package com.hackerda.platform.domain.user;

import com.hackerda.platform.domain.student.StudentAccount;

public interface UserRepository {

    AppStudentUserBO findByStudentAccount(StudentAccount account);

    void store(AppStudentUserBO appStudentUserBO);
}
