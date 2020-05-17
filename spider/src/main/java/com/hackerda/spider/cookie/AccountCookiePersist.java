package com.hackerda.spider.cookie;


import java.net.HttpCookie;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 该接口定义了账号与cookie绑定存储和查询更新的操作
 *
 * @author junrong.chen
 */
public interface AccountCookiePersist<T> {
    /**
     * 返回所有未过期的cookie
     *
     * @return 所有账号与缓存的cookie的集合
     */
    Map<T, List<HttpCookie>> loadAll();


    /**
     * 获取对应账号的cookie
     *
     * @param account cookie对应的学号
     */
    List<HttpCookie> getByAccount(T account);

    /**
     * 为对应账号保存给定的cookie
     *
     * @param cookies 需要保存的cookie
     * @param account cookie对应的学号
     */
    void saveByAccount(List<HttpCookie> cookies, T account);

    /**
     * 删除对应账号指定的cookie
     *
     * @param cookies 需要删除的cookie
     * @param account 对应的账号
     */
    void removeByAccount(List<HttpCookie> cookies, T account);

    /**
     * 删除对应学号所有的cookie
     *
     * @param account 对用账号
     */
    void clearByAccount(T account);
}
