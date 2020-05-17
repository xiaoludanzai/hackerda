package com.hackerda.spider.client;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import org.springframework.http.client.ClientHttpRequestFactory;

import java.net.HttpCookie;
import java.util.List;


/**
 * 该client 允许绑定对应的账号，以此来存取与账号对应的缓存
 * <p>
 * 默认的cookie存储是使用内存
 * 可以通过实现 {@link AccountCookiePersist} 来提供不动存储中间件的支持
 *
 * @param <T> 账号对象
 * @author JR Chan
 */
public class UrpRestTemplate<T> extends RestTemplateWithCookie implements AccountRestTemplate<T> {

    private final TransmittableThreadLocal<T> accountTL = new TransmittableThreadLocal<>();

    private final AccountCookiePersist<T> cookiePersistor;

    public UrpRestTemplate() {
        cookiePersistor = new MemoryCookiePersist<>();
    }

    public UrpRestTemplate(AccountCookiePersist<T> accountCookiePersist) {
        this.cookiePersistor = accountCookiePersist;
    }


    public UrpRestTemplate(ClientHttpRequestFactory factory, AccountCookiePersist<T> accountCookiePersist) {
        super(factory);
        this.cookiePersistor = accountCookiePersist;
    }


    @Override
    public List<HttpCookie> getCookies() {
        return cookiePersistor.getByAccount(getAccount());
    }

    @Override
    public void setCookies(List<HttpCookie> cookieList) {
        cookiePersistor.saveByAccount(cookieList, getAccount());
    }


    @Override
    public void setAccount(T account) {
        accountTL.set(account);
    }

    @Override
    public T getAccount() {
        return accountTL.get();
    }


}
