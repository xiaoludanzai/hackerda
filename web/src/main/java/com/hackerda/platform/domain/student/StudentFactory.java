package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.user.Gender;

public interface StudentFactory {

    WechatStudentUserBO createByClazzNum(StudentAccount account, String name, Gender gender, String urpClazzNum);
}
