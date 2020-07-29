package com.hackerda.platform.service;

import com.hackerda.spider.UrpSpider;
import com.hackerda.spider.support.grade.UrpGeneralGrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewUrpSpiderServiceTest {

    @Autowired
    private UrpSpider urpBaseSpider;

    @Test
    public void getCurrentGeneralGrade() {

        urpBaseSpider.login("2017025838", "1");
        List<UrpGeneralGrade> grade = urpBaseSpider.getCurrentGeneralGrade();
        for (UrpGeneralGrade generalGrade : grade) {
            System.out.println(generalGrade);
        }


    }
}