package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.StudentUserMapper;
import com.hackerda.platform.pojo.Role;
import com.hackerda.platform.pojo.WechatStudentUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface StudentUserExtMapper extends StudentUserMapper {

    List<Role> selectRoleByAccount(Integer account);


    List<WechatStudentUserDO> getWechatUserByAccount(Integer account);

    List<WechatStudentUserDO> getWechatUserByAccountList(Collection<Integer> accountList);
}
