package com.hackerda.platform.infrastructure.repository.user;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.AppUserBO;
import com.hackerda.platform.domain.user.RoleBO;
import com.hackerda.platform.domain.user.UserRepository;
import com.hackerda.platform.infrastructure.database.dao.rbac.RoleDao;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserAdapter userAdapter;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public AppStudentUserBO findByStudentAccount(StudentAccount account) {
        User user = userDao.selectByStudentAccount(account.getAccount());
        return userAdapter.toStudentBO(user, account);
    }

    @Override
    public AppUserBO findByUserName(String userName) {
        User user = userDao.selectByUserName(userName);
        return userAdapter.toBO(user);
    }


    @Transactional
    @Override
    public void store(AppStudentUserBO appStudentUserBO) {

        User user = userAdapter.toDO(appStudentUserBO);

        userDao.insert(user);

        saveOrUpdateStudentRelative(appStudentUserBO, user);

        if(CollectionUtils.isNotEmpty(appStudentUserBO.getRoleList())) {
            roleDao.insertUserRoleRelative(user.getUserName(),
                    appStudentUserBO.getRoleList().stream()
                            .map(RoleBO ::getCode)
                            .collect(Collectors.toList()));
        }


    }

    @Transactional
    @Override
    public void update(AppStudentUserBO appStudentUserBO) {
        User user = userAdapter.toDO(appStudentUserBO);

        saveOrUpdateStudentRelative(appStudentUserBO, user);

        userDao.updateByUserNameSelective(user);

    }

    @Override
    public void update(AppUserBO appUserBO) {
        User user = userAdapter.toDO(appUserBO);

        userDao.updateByUserNameSelective(user);
    }

    private void saveOrUpdateStudentRelative(AppStudentUserBO appStudentUserBO, User user) {
        if(appStudentUserBO.isNormalStatus()) {
            userDao.insertStudentRelative(user.getUserName(), appStudentUserBO.getAccount().getAccount());
        } else if(appStudentUserBO.isLogoutStatus()) {
            userDao.deleteRelativeByStudent(appStudentUserBO.getAccount().getAccount());
        }

    }

}
