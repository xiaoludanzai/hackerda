package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.RoleMapper;
import com.hackerda.platform.infrastructure.database.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface RoleExtMapper extends RoleMapper {

    List<Permission> selectPermissionById(Integer roleId);
}
