package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.ClassCourseTimetableMapper;
import com.hackerda.platform.infrastructure.database.model.ClassCourseTimetable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface ClassCourseTimeTableExtMapper extends ClassCourseTimetableMapper {

    void insertBatch(List<ClassCourseTimetable> list);
}
