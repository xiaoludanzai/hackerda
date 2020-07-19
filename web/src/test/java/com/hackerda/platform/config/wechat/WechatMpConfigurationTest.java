package com.hackerda.platform.config.wechat;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@ActiveProfiles("prod")
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatMpConfigurationTest {

    @Autowired
    private WechatMpConfiguration wechatMpConfiguration;

    @Test
    public void setPlusMenu() {
        wechatMpConfiguration.setPlusMenu();
    }
}