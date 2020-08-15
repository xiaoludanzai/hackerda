package com.hackerda.platform.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("beta")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void store() {
        RoleBO roleBO = new RoleBO("普通用户", RoleBO.USER);

        roleBO.grantPermission(new PermissionBO("查看", PermissionBO.VIEW, true));
        roleBO.grantPermission(new PermissionBO("评论", PermissionBO.COMMENT, true));

        roleRepository.store(roleBO);

        RoleBO code = roleRepository.findByCode(RoleBO.USER);

        assertThat(code.equals(roleBO)).isTrue();


    }

    @Test
    public void findByCode() {

    }
}