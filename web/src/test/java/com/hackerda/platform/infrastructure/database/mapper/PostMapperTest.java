package com.hackerda.platform.infrastructure.database.mapper;

import com.github.pagehelper.PageHelper;
import com.hackerda.platform.domain.community.IdentityCategory;
import com.hackerda.platform.domain.community.RecordStatus;
import com.hackerda.platform.infrastructure.database.mapper.ext.PostExtMapper;
import com.hackerda.platform.infrastructure.database.model.Post;
import com.hackerda.platform.infrastructure.database.model.PostExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("prod")
@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostExtMapper postExtMapper;

    @Test
    public void hideAll() {

        PostExample example = new PostExample();
        example.createCriteria()
                .andRecordStatusEqualTo(RecordStatus.Release.getCode())
                .andIdentityCodeEqualTo(IdentityCategory.Anonymous.getCode());


        List<Post> posts = postExtMapper.selectByExample(example);

        for (Post post : posts) {
            post.setRecordStatus(RecordStatus.Hide.getCode());
            postExtMapper.updateByPrimaryKeySelective(post);

        }


        System.out.println(posts.size());

    }


    @Test
    public void showAll() {

        PostExample example = new PostExample();
        example.createCriteria()
                .andRecordStatusEqualTo(RecordStatus.Hide.getCode())
                .andIdentityCodeEqualTo(IdentityCategory.Anonymous.getCode());


        List<Post> posts = postExtMapper.selectByExample(example);

        for (Post post : posts) {
            post.setRecordStatus(RecordStatus.Release.getCode());
            postExtMapper.updateByPrimaryKeySelective(post);

        }


        System.out.println(posts.size());

    }


    @Test
    public void pageHelper() {
        PostExample postExample = new PostExample();
        postExample.setOrderByClause("id desc");

        PageHelper.startPage(0, 5);
        for (Post post : postExtMapper.selectByExample(postExample)) {
            System.out.println(post);
        }
    }
}