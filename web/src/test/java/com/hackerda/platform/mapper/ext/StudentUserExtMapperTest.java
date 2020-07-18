package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.domain.student.WechatOpenidBO;
import com.hackerda.platform.pojo.WechatStudentUserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ActiveProfiles("prod")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentUserExtMapperTest {

    @Autowired
    private StudentUserExtMapper studentUserExtMapper;

    @Test
    public void selectByOpenIdList() {

        List<WechatStudentUserDO> list = studentUserExtMapper.getWechatUserByAccount(2017025838);

        Map<WechatOpenidBO, List<WechatStudentUserDO>> map = list.stream()
                .collect(Collectors.groupingBy(x-> new WechatOpenidBO(x.getOpenId(), x.getIsBind(), x.getAppId()), Collectors.toList()));
        System.out.println(map);
    }
}