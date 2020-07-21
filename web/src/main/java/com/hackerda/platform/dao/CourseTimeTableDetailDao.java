package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.CourseTimeTableDetailMapper;
import com.hackerda.platform.pojo.CourseTimeTableDetail;
import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.Term;
import com.hackerda.platform.pojo.example.CourseTimeTableDetailExample;
import com.hackerda.platform.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuki
 * @date 2019/8/30 10:12
 */
@Service
public class CourseTimeTableDetailDao {

    @Resource
    private CourseTimeTableDetailMapper courseTimeTableDetailMapper;


    public void insertCourseTimeTableDetail(CourseTimeTableDetail detail){
        courseTimeTableDetailMapper.insertSelective(detail);
    }

    public List<CourseTimeTableDetail> getCourseTimeTableDetailForSection(List<Integer> detailIdList, SchoolTime schoolTime, int section){
        CourseTimeTableDetailExample example = new CourseTimeTableDetailExample();
        example.createCriteria()
                .andIdIn(detailIdList)
                .andTermYearEqualTo(schoolTime.getTerm().getTermYear())
                .andTermOrderEqualTo(schoolTime.getTerm().getOrder())
                .andDayEqualTo(schoolTime.getDay())
                .andStartWeekLessThanOrEqualTo(schoolTime.getSchoolWeek())
                .andEndWeekGreaterThanOrEqualTo(schoolTime.getSchoolWeek())
                .andDistinctNotEqualTo(DateUtils.getContraryDistinct())
                .andOrderEqualTo(section);
        return courseTimeTableDetailMapper.selectByExample(example);
    }

    public List<CourseTimeTableDetail> getCourseTimeTableDetailForCurrentDay(List<Integer> detailIdList, SchoolTime schoolTime){
        CourseTimeTableDetailExample example = new CourseTimeTableDetailExample();
        example.createCriteria()
                .andIdIn(detailIdList)
                .andTermYearEqualTo(schoolTime.getTerm().getTermYear())
                .andTermOrderEqualTo(schoolTime.getTerm().getOrder())
                .andDayEqualTo(schoolTime.getDay())
                .andStartWeekLessThanOrEqualTo(schoolTime.getSchoolWeek())
                .andEndWeekGreaterThanOrEqualTo(schoolTime.getSchoolWeek())
                .andDistinctNotEqualTo(DateUtils.getContraryDistinct());
        return courseTimeTableDetailMapper.selectByExample(example);
    }

    public List<Integer> getCourseTimeTableDetailIdsByClassId(int classesId){
        return courseTimeTableDetailMapper.getCourseTimeTableDetailIdsByClassId(classesId);
    }

    public List<CourseTimeTableDetail> getByClassroomName(String classroomName){
        CourseTimeTableDetailExample example = new CourseTimeTableDetailExample();
        example.createCriteria().andRoomNameEqualTo(classroomName);
        return courseTimeTableDetailMapper.selectByExample(example);
    }

    public List<CourseTimeTableDetail> getCourseTimeTableDetailForCurrentTerm(List<Integer> detailIdList, SchoolTime schoolTime){
        CourseTimeTableDetailExample example = new CourseTimeTableDetailExample();
        example.createCriteria()
                .andIdIn(detailIdList)
                .andTermYearEqualTo(schoolTime.getTerm().getTermYear())
                .andTermOrderEqualTo(schoolTime.getTerm().getOrder());
        return courseTimeTableDetailMapper.selectByExample(example);
    }

    public List<CourseTimeTableDetail> getCourseTimeTableDetailForCurrentWeek(List<Integer> detailIdList,
                                                                              SchoolTime schoolTime){

        CourseTimeTableDetailExample example = new CourseTimeTableDetailExample();
        example.createCriteria()
                .andIdIn(detailIdList)
                .andTermYearEqualTo(schoolTime.getTerm().getTermYear())
                .andTermOrderEqualTo(schoolTime.getTerm().getOrder())
                .andStartWeekLessThanOrEqualTo(schoolTime.getSchoolWeek())
                .andEndWeekGreaterThanOrEqualTo(schoolTime.getSchoolWeek())
                .andDistinctNotEqualTo(DateUtils.getContraryDistinct());
        return courseTimeTableDetailMapper.selectByExample(example);
    }

    public List<CourseTimeTableDetail> selectByDetail(CourseTimeTableDetail detail){
        CourseTimeTableDetailExample example = new CourseTimeTableDetailExample();


        CourseTimeTableDetailExample.Criteria criteria = example.createCriteria()
                .andDayEqualTo(detail.getDay())
                .andOrderEqualTo(detail.getOrder());

        if(detail.getTermYear() != null){
            Term term = detail.getTermForCourseTimeTableDetail();
            criteria.andTermYearEqualTo(term.getTermYear());
        }
        if(detail.getTermOrder() != null){
            criteria.andTermOrderEqualTo(detail.getTermOrder());
        }
        if(detail.getRoomName() != null){
            criteria.andRoomNameEqualTo(detail.getRoomName());
        }

        if(detail.getCourseId() != null){
            criteria.andCourseIdEqualTo(detail.getCourseId());
        }
        if(detail.getStartWeek() != null){
            criteria.andStartWeekEqualTo(detail.getStartWeek());
        }
        if(detail.getEndWeek() != null){
            criteria.andEndWeekEqualTo(detail.getEndWeek());
        }
        if(detail.getWeek() != null){
            criteria.andWeekEqualTo(detail.getWeek());
        }

        return courseTimeTableDetailMapper.selectByExample(example);
    }

    public void insertStudentCourseTimeTableBatch(List<Integer> courseTimeTableIdList, int account,  String termYear,  int termOrder){
        courseTimeTableDetailMapper.insertStudentCourseTimeTableBatch(courseTimeTableIdList, account ,termYear ,termOrder);
    }

}
