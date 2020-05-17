package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.GradeMapper;
import com.hackerda.platform.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface GradeExtMapper extends GradeMapper {

    void insertBatch(List<Grade> gradeList);
}
