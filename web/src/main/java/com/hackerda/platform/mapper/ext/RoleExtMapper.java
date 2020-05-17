package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.RoleMapper;
import com.hackerda.platform.pojo.Permission;
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
