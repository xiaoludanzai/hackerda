package com.hackerda.spider.cookie;


import org.springframework.data.redis.core.RedisOperations;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

/**
 * cookie缓存的redis持久化实现
 *
 * @author JR Chan
 */
public class AccountCookieRedisPersist<T> implements AccountCookiePersist<T> {

    private final RedisOperations<String, String> redisOperations;

    public AccountCookieRedisPersist(RedisOperations<String, String> redisOperations) {
        this.redisOperations = redisOperations;
    }


    @Override
    public Map<T, List<HttpCookie>> loadAll() {
        return null;
    }

    @Override
    public List<HttpCookie> getByAccount(T account) {
        return null;
    }

    @Override
    public void saveByAccount(List<HttpCookie> cookies, T account) {

    }

    @Override
    public void removeByAccount(List<HttpCookie> cookies, T account) {

    }

    @Override
    public void clearByAccount(T account) {

    }
}
