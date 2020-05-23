package com.hackerda.spider.client;

import org.springframework.web.client.RestOperations;

import java.net.HttpCookie;
import java.util.List;

/**
 * 该接口声明了可以设置账号的爬虫客户端应该支持的操作
 * <p>
 * {@link #setCookies} 操作允许对账号的cookie进行动态的更新和绑定
 *
 * @param <T> 账号信息
 */
public interface AccountRestTemplate<T> extends RestOperations {

    void setAccount(T account);

    T getAccount();

    void setCookies(List<HttpCookie> listCookie);

    boolean hasLogin();

    void clearLoginInfo();
}
