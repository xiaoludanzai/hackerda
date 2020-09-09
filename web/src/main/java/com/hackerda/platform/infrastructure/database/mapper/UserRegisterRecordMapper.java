package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.UserRegisterRecord;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRegisterRecordMapper {
    long countByExample(UserRegisterRecordExample example);

    int deleteByExample(UserRegisterRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRegisterRecord record);

    int insertSelective(UserRegisterRecord record);

    List<UserRegisterRecord> selectByExample(UserRegisterRecordExample example);

    UserRegisterRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRegisterRecord record, @Param("example") UserRegisterRecordExample example);

    int updateByExample(@Param("record") UserRegisterRecord record, @Param("example") UserRegisterRecordExample example);

    int updateByPrimaryKeySelective(UserRegisterRecord record);

    int updateByPrimaryKey(UserRegisterRecord record);
}