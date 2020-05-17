package com.hackerda.spider.cookie;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MemoryCookiePersist<T> implements AccountCookiePersist<T> {

    private final Cache<T, List<HttpCookie>> accountCookieCache;

    public MemoryCookiePersist() {
        accountCookieCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(20L, TimeUnit.MINUTES)
                .build();
    }

    public MemoryCookiePersist(Cache<T, List<HttpCookie>> accountCookieCache) {
        this.accountCookieCache = accountCookieCache;
    }


    @Override
    public Map<T, List<HttpCookie>> loadAll() {
        return accountCookieCache.asMap();
    }

    @Override
    public List<HttpCookie> getByAccount(T account) {
        return accountCookieCache.getIfPresent(account);
    }

    @Override
    public void saveByAccount(List<HttpCookie> cookies, T account) {
        accountCookieCache.put(account, cookies);
    }

    @Override
    public void removeByAccount(List<HttpCookie> cookies, T account) {

    }

    @Override
    public void clearByAccount(T account) {
        accountCookieCache.invalidate(account);
    }
}
