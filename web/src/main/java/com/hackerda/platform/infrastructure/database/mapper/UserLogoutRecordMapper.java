package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.UserLogoutRecord;
import com.hackerda.platform.infrastructure.database.model.UserLogoutRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLogoutRecordMapper {
    long countByExample(UserLogoutRecordExample example);

    int deleteByExample(UserLogoutRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserLogoutRecord record);

    int insertSelective(UserLogoutRecord record);

    List<UserLogoutRecord> selectByExample(UserLogoutRecordExample example);

    UserLogoutRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserLogoutRecord record, @Param("example") UserLogoutRecordExample example);

    int updateByExample(@Param("record") UserLogoutRecord record, @Param("example") UserLogoutRecordExample example);

    int updateByPrimaryKeySelective(UserLogoutRecord record);

    int updateByPrimaryKey(UserLogoutRecord record);
}