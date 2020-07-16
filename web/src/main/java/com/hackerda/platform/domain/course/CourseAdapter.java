package com.hackerda.platform.domain.course;

import com.hackerda.platform.pojo.vo.CourseVO;
import org.springframework.stereotype.Service;

@Service
public class CourseAdapter {


    public CourseVO adapter2VO(CourseBO bo){
        CourseVO course = new CourseVO();
        course.setCredit(bo.getCredit());
        course.setExamType(bo.getExamType());
        course.setExamTypeCode(bo.getExamTypeCode());
        course.setCourseOrder(bo.getCourseOrder());
        course.setNum(bo.getNum());
        course.setName(bo.getName());
        course.setTermYear(bo.getTermYear());
        course.setTermOrder(bo.getTermOrder());

        return course;
    }

}
