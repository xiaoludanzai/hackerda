package com.hackerda.platform.infrastructure.repository;

import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.domain.student.StudentAccount;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class PosterRepositoryImplTest {

    @Autowired
    private PosterRepository posterRepository;

    @Test
    public void findByStudentAccount() {
        StudentAccount account = new StudentAccount("2014025838");
        StudentPoster poster = posterRepository.findByStudentAccount(account);

        System.out.println(poster);
    }

    @Test
    public void save() {
        List<ImageInfo> imageInfos = Lists.newArrayList(new ImageInfo("test1", "test1"), new ImageInfo("test2", "test2"));
        PostBO postBO = new PostBO("2014025838", "测试内容", imageInfos, IdentityCategory.Community);
        postBO.setStatus(RecordStatus.Release);
        posterRepository.save(postBO);


    }
}