package com.hackerda.spider.client;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.hackerda.spider.cookie.AccountCookiePersist;
import com.hackerda.spider.cookie.MemoryCookiePersist;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;

import javax.naming.OperationNotSupportedException;
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

    private final AccountCookiePersist<T> cookiePersist;

    public UrpRestTemplate() {
        cookiePersist = new MemoryCookiePersist<>();
    }

    public UrpRestTemplate(AccountCookiePersist<T> accountCookiePersist) {
        this.cookiePersist = accountCookiePersist;
    }

    public UrpRestTemplate(ClientHttpRequestFactory factory, AccountCookiePersist<T> accountCookiePersist) {
        super(factory);
        this.cookiePersist = accountCookiePersist;
    }


    @Override
    public List<HttpCookie> getCookies() {

        return cookiePersist.getByAccount(getAccount());
    }

    @Override
    public void setCookiesFromResponse(HttpHeaders headers) {
        cookiePersist.saveByAccount(super.processHeadersCookie(headers), getAccount());
    }

    @Override
    public void setCookies(List<HttpCookie> cookieList) {
        cookiePersist.saveByAccount(cookieList, getAccount());
    }

    @Override
    public boolean hasLogin() {
        return !(cookiePersist.getByAccount(getAccount()) == null);
    }

    @Override
    public void clearLoginInfo() {
        cookiePersist.clearByAccount(getAccount());
    }

    @Override
    public void setAccount(T account) {
        accountTL.set(account);
    }

    @Override
    public T getAccount() {
        T acoount = accountTL.get();
        if(acoount == null){
            throw new UnsupportedOperationException("account has not set");
        }

        return acoount;
    }


}
