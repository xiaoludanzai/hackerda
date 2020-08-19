package com.hackerda.platform.infrastructure.database.dao.user;

import com.hackerda.platform.domain.community.StudentPoster;
import com.hackerda.platform.infrastructure.database.mapper.ext.UserExtMapper;
import com.hackerda.platform.infrastructure.database.model.StudentPosterDO;
import com.hackerda.platform.infrastructure.database.model.User;
import com.hackerda.platform.infrastructure.database.model.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDao {

    @Autowired
    private UserExtMapper userExtMapper;

    public User selectByMobile(String mobile) {
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);

        return userExtMapper.selectByExample(example).stream().findFirst().orElse(null);
    }

    public User selectByUserName(String userName) {
        UserExample example = new UserExample();
        example.createCriteria().andUserNameEqualTo(userName);

        return userExtMapper.selectByExample(example).stream().findFirst().orElse(null);
    }

    public void insert(User user) {
        if(user == null) {
            log.warn("user is empty");
            return;
        }
        userExtMapper.insertSelective(user);
    }

    public void insertStudentRelative(String userName, String studentAccount) {
        userExtMapper.insertStudentRelative(userName, studentAccount);
    }

    public User selectByStudentAccount(String studentAccount) {
        return userExtMapper.selectByStudentAccount(studentAccount).stream().findFirst().orElse(null);
    }

    public StudentPosterDO selectByStudentPoster(String studentAccount) {
        return userExtMapper.selectByStudentPoster(studentAccount);
    }
}
