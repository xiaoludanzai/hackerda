package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.domain.community.IdentityCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;



@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("prod")
@SpringBootTest
public class IdentityCategoryRedisFilterScript {

    @Autowired
    private IdentityCategoryRedisFilter identityCategoryRedisFilter;


    @Test
    public void add() {

        identityCategoryRedisFilter.add(IdentityCategory.Anonymous);
        identityCategoryRedisFilter.add(IdentityCategory.College);
        identityCategoryRedisFilter.add(IdentityCategory.Grade);
        System.out.println(identityCategoryRedisFilter.userChooseFilter(IdentityCategory.Anonymous));
    }

    @Test
    public void remove() {

        identityCategoryRedisFilter.remove(IdentityCategory.Anonymous);
        identityCategoryRedisFilter.remove(IdentityCategory.College);
        identityCategoryRedisFilter.remove(IdentityCategory.Grade);
        System.out.println(identityCategoryRedisFilter.userChooseFilter(IdentityCategory.Anonymous));
    }

}