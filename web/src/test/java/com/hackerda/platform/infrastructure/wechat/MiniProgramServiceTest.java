package com.hackerda.platform.infrastructure.wechat;

import com.hackerda.platform.infrastructure.database.dao.ImageDao;
import com.hackerda.platform.infrastructure.database.mapper.ext.ImageInfoExtMapper;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class MiniProgramServiceTest {

    @Autowired
    private MiniProgramService miniProgramService;
    @Autowired
    private ImageInfoExtMapper imageInfoExtMapper;

    @Test
    public void checkMsg() {

        boolean b1 = miniProgramService.isSecurityContent("特3456书yuuo莞6543李zxcz蒜7782法fgnv级\n" +
                "完2347全dfji试3726测asad感3847知qwez到");

        boolean b2 = miniProgramService.isSecurityContent("毛泽东");

        System.out.println(b1);
        System.out.println(b2);

    }


    @Test
    public void checkImg() throws UnsupportedEncodingException {

        ImageInfoDO imageInfoDO = imageInfoExtMapper.selectByPrimaryKey(3L);

        String url = imageInfoDO.getUrl();

        String decode = URLDecoder.decode(url, StandardCharsets.UTF_8.name());

        RestTemplate template = new RestTemplate();

        ResponseEntity<byte[]> entity = template.getForEntity(decode, byte[].class);


        boolean b1 = miniProgramService.isSecurityImage(entity.getBody());

        System.out.println(b1);


    }
}