package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.mapper.ext.PostExtMapper;
import com.hackerda.platform.infrastructure.database.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostExtMapper postExtMapper;

    @Test
    public void selectShowPost() {
        List<Post> posts = postExtMapper.selectShowPost(null, 20);
        System.out.println(posts);

    }
}