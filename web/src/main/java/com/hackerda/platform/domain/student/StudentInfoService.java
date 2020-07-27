package com.hackerda.platform.domain.student;

public interface StudentInfoService {

    boolean checkPasswordValid(String account, String enablePassword);

    boolean checkCanBind(String account, String openid);

    StudentUserBO getStudentInfo(String account, String enablePassword);
}
