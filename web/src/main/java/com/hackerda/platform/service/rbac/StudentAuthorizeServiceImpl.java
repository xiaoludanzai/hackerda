package com.hackerda.platform.service.rbac;

import com.hackerda.platform.application.StudentBindApp;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.vo.StudentUserDetailVO;
import com.hackerda.platform.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * @author JR Chan
 */
@Service
public class StudentAuthorizeServiceImpl implements UserAuthorizeService{

    @Autowired
    private StudentBindApp studentBindApp;


    @Override
    public StudentUserDetailVO studentAuthorize(@Nonnull String account, @Nonnull String password, @Nonnull String appId, @Nonnull String openid) {
        StudentUserBO studentUser = studentBindApp.bindByOpenId(account, password, appId, openid);

        return getVO(studentUser);
    }

    @Override
    public StudentUserDetailVO appStudentAuthorize(@Nonnull String account, @Nonnull String password, @Nonnull String appId, @Nonnull String code) {
        StudentUserBO studentUser = studentBindApp.bindByCode(account, password, appId, code);

        return getVO(studentUser);
    }

    private StudentUserDetailVO getVO(StudentUserBO studentUser){
        String account = studentUser.getAccount().toString();
        String token = JwtUtils.signForUserDetail(account, new String[0], new String[0], account);

        StudentUserDetailVO vo = new StudentUserDetailVO();

        vo.setAccount(studentUser.getAccount());
        vo.setName(studentUser.getName());
        vo.setSex(studentUser.getSex());
        vo.setEthnic(studentUser.getEthnic());
        vo.setAcademyName(studentUser.getAcademyName());
        vo.setSubjectName(studentUser.getSubjectName());
        vo.setClassName(studentUser.getClassName());
        vo.setToken(token);

        return vo;
    }
}
