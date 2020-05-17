package com.hackerda.platform.service.rbac;

import com.hackerda.platform.pojo.StudentUserDetail;

/**
 * @author JR Chan
 */
public interface UserDetailService {

    /**
     * 根据学生的学号返回他的角色和权限信息
     * @param account 学生学号
     * @return 学生的详细权限信息
     */
    StudentUserDetail getStudentUserDetail(String account);

}
