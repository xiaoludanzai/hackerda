package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.domain.community.IdentityCategory;
import com.hackerda.platform.domain.community.IdentityCategoryFilter;
import com.hackerda.platform.domain.constant.RedisKeys;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class IdentityCategoryRedisFilter implements IdentityCategoryFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public boolean userChooseFilter(IdentityCategory identityCategory) {
        String key = RedisKeys.Identity_Category_Filter.genKey(profiles);

        return BooleanUtils.toBoolean(stringRedisTemplate.opsForSet().isMember(key,
                String.valueOf(identityCategory.getCode())));
    }

    public void add(IdentityCategory identityCategory) {

        String key = RedisKeys.Identity_Category_Filter.genKey(profiles);
        stringRedisTemplate.opsForSet().add(key, String.valueOf(identityCategory.getCode()));
    }

    public void remove(IdentityCategory identityCategory) {
        String key = RedisKeys.Identity_Category_Filter.genKey(profiles);
        stringRedisTemplate.opsForSet().remove(key, String.valueOf(identityCategory.getCode()));
    }
}
