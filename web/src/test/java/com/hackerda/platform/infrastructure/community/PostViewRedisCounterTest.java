package com.hackerda.platform.infrastructure.community;

import com.google.common.collect.Lists;
import com.hackerda.platform.domain.wechat.WechatUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class PostViewRedisCounterTest {

    @Autowired
    private PostViewRedisCounter postViewRedisCounter;


    @Before
    public void init() {
        postViewRedisCounter.reset();
    }

    @Test
    public void testIncrementSamePost() {
        init();
        postViewRedisCounter.increment(1L, "testUserName", false, new WechatUser("test_appId", "test_openid"));
        postViewRedisCounter.increment(1L, "testUserName", false, new WechatUser("test_appId", "test_openid"));
        postViewRedisCounter.increment(1L, "testUserName", false, new WechatUser("test_appId", "test_openid2"));
        long increment = postViewRedisCounter.increment(1L, "testUserName", false, new WechatUser("test_appId",
                "test_openid2"));

        assertThat(increment).isEqualTo(4L);

        assertThat(postViewRedisCounter.countPageViewByPost(1L)).isEqualTo(4L);
        assertThat(postViewRedisCounter.countUserViewByPost(1L)).isEqualTo(2L);


        assertThat(postViewRedisCounter.countUserViewByUser("testUserName")).isEqualTo(2L);
        assertThat(postViewRedisCounter.countPageViewByUser("testUserName")).isEqualTo(4L);

    }


    @Test
    public void testIncrementDiffPost() {
        init();
        postViewRedisCounter.increment(1L, "testUserName", false, new WechatUser("test_appId", "test_openid"));
        postViewRedisCounter.increment(2L, "testUserName", false, new WechatUser("test_appId", "test_openid"));
        postViewRedisCounter.increment(2L, "testUserName", false, new WechatUser("test_appId", "test_openid"));
        long increment = postViewRedisCounter.increment(2L, "testUserName", false, new WechatUser("test_appId",
                "test_openid1"));

        assertThat(increment).isEqualTo(3L);

        assertThat(postViewRedisCounter.countPageViewByPost(2L)).isEqualTo(3L);
        assertThat(postViewRedisCounter.countUserViewByPost(2L)).isEqualTo(2L);


        assertThat(postViewRedisCounter.countUserViewByUser("testUserName")).isEqualTo(2L);
        assertThat(postViewRedisCounter.countPageViewByUser("testUserName")).isEqualTo(4L);

        assertThat(postViewRedisCounter.multiCountPageViewByPost(Lists.newArrayList(1L, 2L))).isEqualTo(Lists.newArrayList(1L, 3L));
        assertThat(postViewRedisCounter.multiCountUserViewByPost(Lists.newArrayList(1L, 2L))).isEqualTo(Lists.newArrayList(1L, 2L));
    }

    @Test
    public void testIncrementAnonymous() {
        init();
        postViewRedisCounter.increment(1L, "testUserName", false, new WechatUser("test_appId", "test_openid"));
        postViewRedisCounter.increment(2L, "testUserName", true, new WechatUser("test_appId", "test_openid"));
        postViewRedisCounter.increment(2L, "testUserName", true, new WechatUser("test_appId", "test_openid"));
        long increment = postViewRedisCounter.increment(2L, "testUserName", true, new WechatUser("test_appId",
                "test_openid1"));

        assertThat(increment).isEqualTo(3L);

        assertThat(postViewRedisCounter.countPageViewByPost(2L)).isEqualTo(3L);
        assertThat(postViewRedisCounter.countUserViewByPost(2L)).isEqualTo(2L);


        assertThat(postViewRedisCounter.countUserViewByUser("testUserName")).isEqualTo(1L);
        assertThat(postViewRedisCounter.countPageViewByUser("testUserName")).isEqualTo(1L);

        assertThat(postViewRedisCounter.multiCountPageViewByPost(Lists.newArrayList(1L, 2L))).isEqualTo(Lists.newArrayList(1L, 3L));
        assertThat(postViewRedisCounter.multiCountUserViewByPost(Lists.newArrayList(1L, 2L))).isEqualTo(Lists.newArrayList(1L, 2L));
    }




    @After
    public void destroy() {
        postViewRedisCounter.reset();
    }


}