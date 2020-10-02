package com.hackerda.platform.infrastructure.community;

import com.google.common.annotations.VisibleForTesting;
import com.hackerda.platform.domain.community.PostViewCounter;
import com.hackerda.platform.domain.constant.RedisKeys;
import com.hackerda.platform.domain.wechat.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostViewRedisCounter implements PostViewCounter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${spring.profiles.active}")
    private String profiles;


    @Override
    public long increment(long postId, String userName, boolean isAnonymous,  WechatUser wechatUser) {
        Long increment = stringRedisTemplate.opsForValue().increment(RedisKeys.POST_PV.genKey(profiles,
                String.valueOf(postId)));

        if(!isAnonymous) {
            stringRedisTemplate.opsForValue().increment(RedisKeys.USER_POST_PV.genKey(profiles, userName));
        }

        if(wechatUser != null && !hasRead(postId, wechatUser)) {
            stringRedisTemplate.opsForSet().add(getPostUV(postId), wechatUser.asValue());
            if(!isAnonymous) {
                stringRedisTemplate.opsForSet().add(RedisKeys.USER_POST_UV.genKey(profiles, userName), wechatUser.asValue());
            }
        }

        return Optional.ofNullable(increment).orElse(0L);
    }


    @Override
    public long countPageViewByPost(long postId) {
        Optional<String> optional =
                Optional.ofNullable(stringRedisTemplate.opsForValue().get(RedisKeys.POST_PV.genKey(profiles,
                String.valueOf(postId))));
        return Long.parseLong(optional.orElse("0"));
    }

    @Override
    public long countUserViewByPost(long postId) {
        Optional<Long> optional =
                Optional.ofNullable(stringRedisTemplate.opsForSet().size(getPostUV(postId)));
        return optional.orElse(0L);
    }

    @Override
    public List<Long> multiCountPageViewByPost(List<Long> postId) {

        List<String> keyList = postId.stream().map(x -> RedisKeys.POST_PV.genKey(profiles,
                String.valueOf(x))).collect(Collectors.toList());

        return Optional.ofNullable(stringRedisTemplate.opsForValue().multiGet(keyList)).orElse(Collections.emptyList())
                .stream().map(value -> Long.parseLong(Optional.ofNullable(value).orElse("0")))
                .collect(Collectors.toList());

    }

    @Override
    public List<Long> multiCountUserViewByPost(List<Long> postId) {
        return postId.stream().map(this::countUserViewByPost).collect(Collectors.toList());
    }

    @Override
    public long countPageViewByUser(String userName) {
        Optional<String> optional =
                Optional.ofNullable(stringRedisTemplate.opsForValue().get(RedisKeys.USER_POST_PV.genKey(profiles, userName)));

        return Long.parseLong(optional.orElse("0"));
    }

    @Override
    public long countUserViewByUser(String userName) {
        Optional<Long> optional =
                Optional.ofNullable(stringRedisTemplate.opsForSet().size(RedisKeys.USER_POST_UV.genKey(profiles,
                        userName)));

        return optional.orElse(0L);
    }

    private boolean hasRead(long postId,  WechatUser wechatUser) {
        return Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(getPostUV(postId), wechatUser.asValue()))
                .orElse(false);

    }

    private String getPostUV(long postId) {
        return RedisKeys.POST_UV.genKey(profiles, String.valueOf(postId));
    }

    @VisibleForTesting
    void reset() {
        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys(RedisKeys.POST_UV.genKey(profiles) +
                "*")));
        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys(RedisKeys.POST_PV.genKey(profiles) +
                "*")));

        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys(RedisKeys.USER_POST_UV.genKey(profiles) +
                "*")));

        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys(RedisKeys.USER_POST_PV.genKey(profiles) +
                "*")));
    }

}
