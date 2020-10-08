package com.hackerda.platform.infrastructure.repository.student;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentFactory;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.dao.UrpClassDao;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.UrpClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StudentFactoryImpl implements StudentFactory {

    @Autowired
    private UrpClassDao urpClassDao;
    @Autowired
    private StudentUserAdapter adapter;


    @Override
    public WechatStudentUserBO createByClazzNum(StudentAccount account, String name, Gender gender,
                                                String urpClazzNum) {

        UrpClass urpClass = urpClassDao.selectByClassNumber(urpClazzNum);

        if(urpClass == null) {
            throw new BusinessException(ErrorCode.NO_DATA, urpClazzNum + "班级号没有数据");
        }

        StudentUser studentUser = new StudentUser();
        studentUser.setAcademyName(urpClass.getAcademyName());
        studentUser.setClassName(urpClass.getClassName());
        studentUser.setSubjectName(urpClass.getSubjectName());
        studentUser.setUrpclassNum(Integer.parseInt(urpClass.getClassNum()));
        studentUser.setAccount(account.getInt());
        studentUser.setName(name);
        studentUser.setSex(gender.getDesc());
        studentUser.setHasCheck(false);
        String uuid = UUID.randomUUID().toString();
        studentUser.setPassword(uuid);
        studentUser.setIsCorrect(false);
        studentUser.setEthnic("");
        WechatStudentUserBO userBO = adapter.toBO(studentUser);
        userBO.setSaveOrUpdate(true);

        return userBO;
    }
}
