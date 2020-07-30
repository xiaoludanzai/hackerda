package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.RolePermissionMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface RolePermissionExtMapper extends RolePermissionMapper {
    void insertBatch(@Param("roleIdList") List<Integer> roleIdList, @Param("permissionIdList") List<Integer> permissionIdList);
}
