package com.hackerda.platform.service;

import com.hackerda.platform.application.CreateStudentApp;
import com.hackerda.platform.controller.request.CreateStudentRequest;
import com.hackerda.platform.controller.vo.StudentUserDetailVO;
import com.hackerda.platform.controller.vo.student.AcademyVO;
import com.hackerda.platform.controller.vo.student.ClazzInfoVO;
import com.hackerda.platform.controller.vo.student.ClazzVO;
import com.hackerda.platform.controller.vo.student.SubjectVO;
import com.hackerda.platform.domain.student.ClazzInfoRepository;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentFactory;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateStudentService {

    @Autowired
    private ClazzInfoRepository clazzinforepository;
    @Autowired
    private StudentFactory studentFactory;
    @Autowired
    private CreateStudentApp createStudentApp;

    public StudentUserDetailVO createStudentUser(CreateStudentRequest createStudentRequest) {

        Gender gender = createStudentRequest.getGender().equals("男") ? Gender.Man : Gender.Woman;

        WechatStudentUserBO studentUserBO = studentFactory.createByClazzNum(new StudentAccount(createStudentRequest.getAccount()),
                createStudentRequest.getName(), gender, createStudentRequest.getClazzNum());


        createStudentApp.createStudentUser(studentUserBO, new WechatUser(createStudentRequest.getAppId(),
                createStudentRequest.getOpenid()));

        return getVO(studentUserBO, createStudentRequest.getAppId());
    }

    private StudentUserDetailVO getVO(WechatStudentUserBO studentUser, String appId){
        String account = studentUser.getAccount().toString();
        String token = JwtUtils.signForWechatStudent(account, appId, studentUser.getOpenid(appId));

        StudentUserDetailVO vo = new StudentUserDetailVO();

        vo.setAccount(studentUser.getAccount().getInt());
        vo.setName(studentUser.getName());
        vo.setSex(studentUser.getSex());
        vo.setEthnic(studentUser.getEthnic());
        vo.setAcademyName(studentUser.getAcademyName());
        vo.setSubjectName(studentUser.getSubjectName());
        vo.setClassName(studentUser.getClassName());
        vo.setToken(token);
        vo.setUseDefaultPassword(studentUser.isUsingDefaultPassword());

        return vo;
    }

    public ClazzInfoVO<AcademyVO> getAcademyByGrade(String grade) {
        ClazzInfoVO<AcademyVO> vo = new ClazzInfoVO<>();
        List<AcademyVO> academyVOList = clazzinforepository.findAcademy(grade).stream().map(x -> {
            AcademyVO academyVO = new AcademyVO();
            academyVO.setGrade(x.getGrade());
            academyVO.setName(x.getName());
            academyVO.setNum(x.getNum());
            return academyVO;
        }).collect(Collectors.toList());

        vo.setGrade(grade);
        vo.setSize(academyVOList.size());
        vo.setInfoList(academyVOList);

        return vo;
    }

    public ClazzInfoVO<SubjectVO> getSubject(String grade, String academyNum) {
        ClazzInfoVO<SubjectVO> vo = new ClazzInfoVO<>();
        List<SubjectVO> academyVOList = clazzinforepository.findSubject(grade, academyNum).stream().map(x -> {
            SubjectVO academyVO = new SubjectVO();
            academyVO.setGrade(x.getGrade());
            academyVO.setName(x.getName());
            academyVO.setNum(x.getNum());
            return academyVO;
        }).collect(Collectors.toList());

        vo.setGrade(grade);
        vo.setSize(academyVOList.size());
        vo.setInfoList(academyVOList);

        return vo;
    }

    public ClazzInfoVO<ClazzVO> getClazz(String grade, String academyNum,  String subjectNum) {
        ClazzInfoVO<ClazzVO> vo = new ClazzInfoVO<>();
        List<ClazzVO> academyVOList = clazzinforepository.findClazz(grade, academyNum, subjectNum).stream().map(x -> {
            ClazzVO academyVO = new ClazzVO();
            academyVO.setGrade(x.getGrade());
            academyVO.setName(x.getName());
            academyVO.setNum(x.getNum());
            return academyVO;
        }).collect(Collectors.toList());

        vo.setGrade(grade);
        vo.setSize(academyVOList.size());
        vo.setInfoList(academyVOList);

        return vo;
    }


}
