package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.ExamTimetableMapper;
import com.hackerda.platform.infrastructure.database.model.example.ExamTimetable;
import com.hackerda.platform.infrastructure.database.model.example.ExamTimetableExample;
import com.hackerda.platform.utils.Term;
import com.hackerda.platform.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hackerda.platform.infrastructure.database.mapper.ExamTimetableDynamicSqlSupport.*;
import static com.hackerda.platform.infrastructure.database.mapper.StudentExamTimetableDynamicSqlSupport.studentExamTimetable;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
public class ExamTimetableDao {
    @Resource
    private ExamTimetableMapper examTimetableMapper;


    public void insertSelective(ExamTimetable examTimetable) {
        examTimetableMapper.insertSelective(examTimetable);
    }

    public ExamTimetable selectByPrimaryKey(Integer id) {
        return examTimetableMapper.selectByPrimaryKey(id).orElse(null);
    }

    public List<ExamTimetable> selectByPojo(ExamTimetable examTimetable) {

        ExamTimetableExample example = new ExamTimetableExample();
        ExamTimetableExample.Criteria criteria = example.createCriteria();

        criteria.andCourseNumEqualTo(examTimetable.getCourseNum());
        criteria.andCourseOrderEqualTo(examTimetable.getCourseOrder());
        criteria.andTermYearEqualTo(examTimetable.getTermYear());
        criteria.andTermOrderEqualTo(examTimetable.getTermOrder());

        return examTimetableMapper.select(c ->
                c.where(courseNum, isEqualToWhenPresent(examTimetable.getCourseNum()))
                        .and(courseOrder, isEqualToWhenPresent(examTimetable.getCourseOrder()))
                        .and(termYear, isEqualToWhenPresent(examTimetable.getTermYear()))
                        .and(termOrder, isEqualToWhenPresent(examTimetable.getTermOrder()))
        );
    }

    public List<ExamTimetable> selectCurrentExamByAccount(String account) {
        Term term = DateUtils.getCurrentSchoolTime().getTerm();
        return examTimetableMapper.select(c ->
                c.join(studentExamTimetable, on(studentExamTimetable.examTimetableId, equalTo(examTimetable.id)))
                .where(studentExamTimetable.account, isEqualTo(account))
                        .and(studentExamTimetable.termOrder, isEqualTo(term.getOrder()))
                        .and(studentExamTimetable.termYear, isEqualTo(term.getTermYear())));
    }

}
