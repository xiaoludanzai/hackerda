package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.CourseTimetableMapper;
import com.hackerda.platform.infrastructure.database.model.ClassCourseTimetable;
import com.hackerda.platform.infrastructure.database.model.CourseTimetable;
import com.hackerda.platform.infrastructure.database.model.CourseTimetableDetailDO;
import com.hackerda.platform.infrastructure.database.model.StudentCourseTimeTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface CourseTimetableExtMapper extends CourseTimetableMapper {
    /**
     * 根据学生课程关系表查询课程信息
     * @param relative  关系对象
     * @return 学生对应课程信息
     */
    List<CourseTimetable> selectByStudentRelative(StudentCourseTimeTable relative);

    /**
     * 根据班级课程关系表查询课程信息
     * @param relative  关系对象
     * @return 班级对应课程信息
     */
    List<CourseTimetable> selectByClassRelative(ClassCourseTimetable relative);

    void insertBatch(List<CourseTimetable> list);

    List<CourseTimetable> selectBatch(List<CourseTimetable> list);


    void insertBatchStudentRelative(List<StudentCourseTimeTable> relativeList);

    List<CourseTimetableDetailDO> selectDetailByStudentAccount(StudentCourseTimeTable relative);

    List<CourseTimetableDetailDO> selectDetailByClassId(ClassCourseTimetable relative);
}
