package com.hackerda.platform.domain.user;

import com.hackerda.platform.domain.student.StudentAccount;

import java.util.List;

public interface UserRegisterRecordRepository {

    void save(UserRegisterRecordBO userRegisterRecordBO);

    List<UserRegisterRecordBO> findByUserName(String userName);

    List<UserRegisterRecordBO> findByStudentAccount(StudentAccount studentAccount);

    void update(UserRegisterRecordBO userRegisterRecordBO);

    void save(LogoutRecordBO logoutRecordBO);
}
