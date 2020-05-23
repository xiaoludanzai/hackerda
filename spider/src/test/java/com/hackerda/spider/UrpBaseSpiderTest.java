package com.hackerda.spider;

import com.google.common.collect.Lists;
import com.hackerda.spider.client.AccountRestTemplate;
import com.hackerda.spider.client.UrpRestTemplate;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import org.junit.Test;

import java.net.HttpCookie;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;


@SuppressWarnings({"rawtypes", "unchecked"})
public class UrpBaseSpiderTest {


    @Test
    public void passwordErrorCallBack() {

        AccountRestTemplate client = mock(AccountRestTemplate.class);
        IExceptionHandler handler = mock(IExceptionHandler.class);

        UrpBaseSpider spider = new UrpBaseSpider(client, null, null, handler);

        assertThatExceptionOfType(PasswordUnCorrectException.class)
                .isThrownBy(() -> spider.checkResult("badCredentials"));

        verify(client).clearLoginInfo();
        verify(handler).handle(any(PasswordUnCorrectException.class), any());
    }


    @Test
    public void testSameAccountCurrentLogin() throws InterruptedException {
        UrpRestTemplate<String> client = spy(new UrpRestTemplate<>());

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger count = new AtomicInteger(0);
        UrpBaseSpider spider = new UrpBaseSpider(client, null, null){
            @Override
            protected void login0(String account, String password) {
                count.getAndIncrement();
                client.setAccount(account);
                client.setCookies(Lists.newArrayList(new HttpCookie("key", "value")));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            }
        };

        new Thread(() -> spider.login("test", "test")).start();
        new Thread(() -> spider.login("test", "test")).start();

        // mock login
        Thread.sleep(2000L);

        assertThat(latch.await(500L, TimeUnit.MILLISECONDS));

        assertThat(count.get() == 1);

        verify(client, times(2)).hasLogin();

        verify(client, times(1)).setCookies(anyList());



    }
}