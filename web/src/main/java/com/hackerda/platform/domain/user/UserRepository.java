package com.hackerda.platform.domain.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {


    UserBO findByStudentAccount(int account);


    
}
