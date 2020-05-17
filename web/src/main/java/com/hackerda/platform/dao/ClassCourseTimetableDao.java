package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.ext.ClassCourseTimeTableExtMapper;
import com.hackerda.platform.pojo.ClassCourseTimetable;
import com.hackerda.platform.pojo.example.ClassCourseTimetableExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassCourseTimetableDao {
    @Resource
    private ClassCourseTimeTableExtMapper classCourseTimeTableExtMapper;

    public List<ClassCourseTimetable> selectByPojo(ClassCourseTimetable classCourseTimetable) {
        ClassCourseTimetableExample example = new ClassCourseTimetableExample();
        ClassCourseTimetableExample.Criteria criteria = example.createCriteria();

        if (classCourseTimetable.getCourseTimetableId() != null) {
            criteria.andCourseTimetableIdEqualTo(classCourseTimetable.getCourseTimetableId());
        }
        if (classCourseTimetable.getClassId() != null) {
            criteria.andClassIdEqualTo(classCourseTimetable.getClassId());
        }

        if (classCourseTimetable.getTermOrder() != null) {
            criteria.andTermOrderEqualTo(classCourseTimetable.getTermOrder());
        }
        if (classCourseTimetable.getTermYear() != null) {
            criteria.andTermYearEqualTo(classCourseTimetable.getTermYear());
        }

        return classCourseTimeTableExtMapper.selectByExample(example);

    }

    public void insertBatch(List<ClassCourseTimetable> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        classCourseTimeTableExtMapper.insertBatch(list);
    }

}
