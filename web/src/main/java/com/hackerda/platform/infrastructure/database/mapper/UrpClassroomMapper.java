package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.UrpClassroom;
import com.hackerda.platform.infrastructure.database.model.example.UrpClassroomExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UrpClassroomMapper {
    int countByExample(UrpClassroomExample example);

    int deleteByExample(UrpClassroomExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UrpClassroom record);

    int insertSelective(UrpClassroom record);

    List<UrpClassroom> selectByExample(UrpClassroomExample example);

    UrpClassroom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UrpClassroom record, @Param("example") UrpClassroomExample example);

    int updateByExample(@Param("record") UrpClassroom record, @Param("example") UrpClassroomExample example);

    int updateByPrimaryKeySelective(UrpClassroom record);

    int updateByPrimaryKey(UrpClassroom record);
}