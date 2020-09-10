package com.hackerda.platform.domain.user;

public interface UserRegisterRecordRepository {

    void save(UserRegisterRecordBO userRegisterRecordBO);

    UserRegisterRecordBO findByUserName(String userName);

    void update(UserRegisterRecordBO userRegisterRecordBO);

    void save(LogoutRecordBO logoutRecordBO);
}
