package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.StudentRole;
import com.hackerda.platform.infrastructure.database.model.example.StudentRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface StudentRoleMapper {
    long countByExample(StudentRoleExample example);

    int deleteByExample(StudentRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentRole record);

    int insertSelective(StudentRole record);

    List<StudentRole> selectByExample(StudentRoleExample example);

    StudentRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentRole record, @Param("example") StudentRoleExample example);

    int updateByExample(@Param("record") StudentRole record, @Param("example") StudentRoleExample example);

    int updateByPrimaryKeySelective(StudentRole record);

    int updateByPrimaryKey(StudentRole record);
}