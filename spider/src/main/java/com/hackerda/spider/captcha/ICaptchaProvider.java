package com.hackerda.spider.captcha;


import lombok.SneakyThrows;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * 该接口指定了提供验证码的操作
 *
 * @param <T> 验证码资源
 * @author JR Chan
 */
public interface ICaptchaProvider<T> {

    /**
     * 获取验证码资源
     *
     * @return 验证码资源
     */
    T get();

    /**
     * 在给定的时间内获取验证码。
     * 等待超过给定的时间后会返回null
     *
     * @param timeout  超时的时间
     * @param timeUnit 时间单位
     * @return 如果在给定时间内成功获取则返回验证码资源，否则返回null
     */
    @Nullable
    T get(long timeout, TimeUnit timeUnit);


}
