package com.hackerda.platform.infrastructure.repository.user;

import com.google.common.collect.Lists;
import com.hackerda.platform.domain.user.PermissionBO;
import com.hackerda.platform.domain.user.RoleBO;
import com.hackerda.platform.domain.user.RoleRepository;
import com.hackerda.platform.infrastructure.database.dao.rbac.PermissionDao;
import com.hackerda.platform.infrastructure.database.dao.rbac.RoleDao;
import com.hackerda.platform.infrastructure.database.dao.rbac.RolePermissionDao;
import com.hackerda.platform.infrastructure.database.model.Permission;
import com.hackerda.platform.infrastructure.database.model.Role;
import com.hackerda.platform.infrastructure.database.model.RoleDetailDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Transactional
    @Override
    public void store(RoleBO roleBO) {
        Role role = new Role().setName(roleBO.getName()).setCode(roleBO.getCode());
        roleDao.insertSelective(role);

        List<Permission> permissionList = roleBO.getPermissionList().stream()
                .filter(PermissionBO::isNew)
                .map(x -> new Permission().setPermissionName(x.getName()).setPermissionCode(x.getCode()))
                .collect(Collectors.toList());

        for (Permission permission : permissionList) {
            permissionDao.insertSelective(permission);
        }

        rolePermissionDao.insertBatch(Lists.newArrayList(role.getId()), permissionList.stream().map(Permission::getId).collect(Collectors.toList()));

    }

    @Override
    public RoleBO findByCode(String code) {
        List<RoleDetailDO> roleDetailList = roleDao.selectRoleDetailByCode(code);

        return transfer(roleDetailList).stream().findFirst().orElse(null);
    }

    @Override
    public List<RoleBO> findByUserName(String userName) {

        List<RoleDetailDO> roleDetailList = roleDao.selectRoleDetailByUserName(userName);

        return transfer(roleDetailList);
    }


    private List<RoleBO> transfer(List<RoleDetailDO> roleDetailList) {

        if(CollectionUtils.isEmpty(roleDetailList)) {
            return Collections.emptyList();
        }

        Map<RoleBO, List<PermissionBO>> collect = roleDetailList.stream()
                .collect(Collectors.groupingBy(x -> new RoleBO(x.getRoleName(), x.getRoleCode()),
                        Collectors.mapping(x -> new PermissionBO(x.getPermissionName(), x.getPermissionCode(), false),
                                Collectors.toList())));

        return collect.keySet().stream()
                .map(x -> new RoleBO(x.getName(), x.getCode(), collect.get(x)))
                .collect(Collectors.toList());
    }
}
