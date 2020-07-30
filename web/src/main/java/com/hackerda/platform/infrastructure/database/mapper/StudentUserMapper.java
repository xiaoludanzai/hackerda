package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.example.StudentUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentUserMapper {
    int countByExample(StudentUserExample example);

    int deleteByExample(StudentUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentUser record);

    int insertSelective(StudentUser record);

    List<StudentUser> selectByExample(StudentUserExample example);

    StudentUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentUser record, @Param("example") StudentUserExample example);

    int updateByExample(@Param("record") StudentUser record, @Param("example") StudentUserExample example);

    int updateByPrimaryKeySelective(StudentUser record);

    int updateByPrimaryKey(StudentUser record);
}