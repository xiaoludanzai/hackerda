package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.ext.CourseTimetableExtMapper;
import com.hackerda.platform.pojo.*;
import com.hackerda.platform.pojo.example.CourseTimetableExample;
import com.hackerda.platform.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseTimeTableDao {
    @Resource
    private CourseTimetableExtMapper courseTimetableExtMapper;


    public CourseTimetable selectByPrimaryKey(Integer id) {
        return courseTimetableExtMapper.selectByPrimaryKey(id);
    }

    public List<CourseTimetable> selectByCourseTimetable(CourseTimetable courseTimetable) {
        CourseTimetableExample example = getExample(courseTimetable);

        return courseTimetableExtMapper.selectByExample(example);
    }

    public CourseTimetable selectUniqueCourse(CourseTimetable courseTimetable) {
        CourseTimetableExample example = new CourseTimetableExample();
        CourseTimetableExample.Criteria criteria = example.createCriteria();

        getUniqueExample(courseTimetable, example, criteria);

        return courseTimetableExtMapper.selectByExample(example).stream().findFirst().orElse(null);

    }

    private CourseTimetableExample getUniqueExample(CourseTimetable courseTimetable, CourseTimetableExample example, CourseTimetableExample.Criteria criteria) {

        if (courseTimetable.getCourseId() != null) {
            criteria.andCourseIdEqualTo(courseTimetable.getCourseId());
        }
        if (courseTimetable.getCourseSequenceNumber() != null) {
            criteria.andCourseSequenceNumberEqualTo(courseTimetable.getCourseSequenceNumber());
        }
        if (courseTimetable.getClassDay() != null) {
            criteria.andClassDayEqualTo(courseTimetable.getClassDay());
        }
        if (courseTimetable.getClassOrder() != null) {
            criteria.andClassOrderEqualTo(courseTimetable.getClassOrder());
        }
        if (courseTimetable.getTermYear() != null) {
            criteria.andTermYearEqualTo(courseTimetable.getTermYear());
        }
        if (courseTimetable.getTermOrder() != null) {
            criteria.andTermOrderEqualTo(courseTimetable.getTermOrder());
        }
        if (courseTimetable.getStartWeek() != null) {
            criteria.andStartWeekEqualTo(courseTimetable.getStartWeek());
        }
        if (courseTimetable.getEndWeek() != null) {
            criteria.andEndWeekEqualTo(courseTimetable.getEndWeek());
        }

        return example;
    }

    private CourseTimetableExample getExample(CourseTimetable courseTimetable){
        CourseTimetableExample example = new CourseTimetableExample();
        CourseTimetableExample.Criteria criteria = example.createCriteria();
        getUniqueExample(courseTimetable, example, criteria);

        if (courseTimetable.getRoomNumber() != null) {
            criteria.andRoomNumberEqualTo(courseTimetable.getRoomNumber());
        }

        if (courseTimetable.getRoomName() != null) {
            criteria.andRoomNameEqualTo(courseTimetable.getRoomName());
        }

        return example;
    }

    public List<CourseTimetable> selectByStudentRelative(StudentCourseTimeTable relative) {

        return courseTimetableExtMapper.selectByStudentRelative(relative);
    }

    public List<CourseTimetable> selectByClassRelative(ClassCourseTimetable relative) {

        return courseTimetableExtMapper.selectByClassRelative(relative);
    }

    public List<CourseTimetable> selectByIdList(List<Integer> idList) {
        CourseTimetableExample example = new CourseTimetableExample();
        CourseTimetableExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idList);

        return courseTimetableExtMapper.selectByExample(example);
    }

    public void insertSelective(CourseTimetable courseTimetable) {
        courseTimetableExtMapper.insertSelective(courseTimetable);
    }

    public void insertBatch(List<CourseTimetable> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        courseTimetableExtMapper.insertBatch(list);
    }

    public void updateByPrimaryKeySelective(CourseTimetable courseTimetable) {
        courseTimetableExtMapper.updateByPrimaryKeySelective(courseTimetable);
    }

    public void updateByUniqueKey(CourseTimetable courseTimetable) {
        CourseTimetableExample example = new CourseTimetableExample();
        CourseTimetableExample.Criteria criteria = example.createCriteria();
        getUniqueExample(courseTimetable, example, criteria);

        courseTimetableExtMapper.updateByExampleSelective(courseTimetable, example);
    }

    public List<CourseTimetable> selectBatchByKey(List<CourseTimetable> list){
        if(CollectionUtils.isEmpty(list)){
            return list;
        }
        return courseTimetableExtMapper.selectBatch(list);
    }

    public List<CourseTimetable> getCurrentTermTableByAccount(Integer account){
        SchoolTime schoolTime = DateUtils.getCurrentSchoolTime();

        StudentCourseTimeTable table = new StudentCourseTimeTable()
                .setStudentId(account)
                .setTermOrder(schoolTime.getTerm().getOrder())
                .setTermYear(schoolTime.getTerm().getTermYear());
        return courseTimetableExtMapper.selectByStudentRelative(table);
    }

    public void insertBatchStudentRelative(List<StudentCourseTimeTable> relativeList){
        courseTimetableExtMapper.insertBatchStudentRelative(relativeList);
    }

    public List<CourseTimetableDetailDO> selectDetailByStudent(StudentCourseTimeTable relative){
        if(relative == null){
            return Collections.emptyList();
        }
        return courseTimetableExtMapper.selectDetailByStudentAccount(relative).stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<CourseTimetableDetailDO> selectDetailByClassId(ClassCourseTimetable relative){
        if(relative == null){
            return Collections.emptyList();
        }
        return courseTimetableExtMapper.selectDetailByClassId(relative);
    }

}
