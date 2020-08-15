package com.hackerda.platform.domain.user;

import java.util.List;

public interface RoleRepository {

    void store(RoleBO roleBO);

    RoleBO findByCode(String code);

    List<RoleBO> findByUserName(String userName);
}
