package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.UserMapper;
import com.hackerda.platform.infrastructure.database.model.StudentPosterDO;
import com.hackerda.platform.infrastructure.database.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserExtMapper extends UserMapper {


    void insertStudentRelative(String userName, String studentAccount);

    List<User> selectByStudentAccount(String studentAccount);

    StudentPosterDO selectByStudentPoster(String studentAccount);

    StudentPosterDO selectStudentPosterByUserName(String userName);

    String selectRelativeUserNameByStudentAccount(String studentAccount);

    String selectRelativeStudentAccountByUserName(String userName);

    void updateRelativeUserNameByStudentAccount(String userName, String studentAccount);
}
