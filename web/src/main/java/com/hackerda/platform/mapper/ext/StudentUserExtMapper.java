package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.StudentUserMapper;
import com.hackerda.platform.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface StudentUserExtMapper extends StudentUserMapper {

    List<Role> selectRoleByAccount(Integer account);
}
