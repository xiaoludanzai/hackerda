package com.hackerda.platform.infrastructure.wechat;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class MiniProgramServiceTest {

    @Autowired
    private MiniProgramService miniProgramService;

    @Test
    public void checkMsg() {

        boolean b1 = miniProgramService.isSecurityContent("特3456书yuuo莞6543李zxcz蒜7782法fgnv级\n" +
                "完2347全dfji试3726测asad感3847知qwez到");

        boolean b2 = miniProgramService.isSecurityContent("毛泽东");

        System.out.println(b1);
        System.out.println(b2);

    }
}