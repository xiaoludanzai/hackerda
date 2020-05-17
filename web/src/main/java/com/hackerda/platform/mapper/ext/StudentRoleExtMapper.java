package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.StudentRoleMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface StudentRoleExtMapper extends StudentRoleMapper {

    /**
     * 为学生批量添加角色
     * @param account 学生学号
     * @param roleIdList 角色id列表
     */
    void insertBatch(@Param("account") int account, @Param("roleIdList") List<Integer> roleIdList);
}
