package com.hackerda.platform.service;

import com.hackerda.platform.controller.vo.StudentUserDetailVO;
import com.hackerda.platform.controller.vo.student.AcademyVO;
import com.hackerda.platform.controller.vo.student.ClazzInfoVO;
import com.hackerda.platform.controller.vo.student.ClazzVO;
import com.hackerda.platform.controller.vo.student.SubjectVO;
import com.hackerda.platform.domain.student.ClazzInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateStudentService {

    @Autowired
    private ClazzInfoRepository clazzinforepository;

    public StudentUserDetailVO createStudentUser() {

        return null;
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
