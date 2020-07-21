package com.hackerda.platform.mapper;

import com.hackerda.platform.pojo.CourseTimeTableDetail;
import com.hackerda.platform.pojo.example.CourseTimeTableDetailExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseTimeTableDetailMapper {
    int countByExample(CourseTimeTableDetailExample example);

    int deleteByExample(CourseTimeTableDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CourseTimeTableDetail record);

    int insertSelective(CourseTimeTableDetail record);

    List<CourseTimeTableDetail> selectByExample(CourseTimeTableDetailExample example);

    CourseTimeTableDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CourseTimeTableDetail record, @Param("example") CourseTimeTableDetailExample example);

    int updateByExample(@Param("record") CourseTimeTableDetail record, @Param("example") CourseTimeTableDetailExample example);

    int updateByPrimaryKeySelective(CourseTimeTableDetail record);

    int updateByPrimaryKey(CourseTimeTableDetail record);

    List<Integer> getCourseTimeTableDetailIdsByClassId(@Param("classesId") int classesId);

    void insertStudentCourseTimeTableBatch(@Param("courseTimeTableIdList") List<Integer> courseTimeTableIdList,
                                           @Param("account") int account, @Param("termYear") String termYear, @Param(
            "termOrder") int termOrder);

}