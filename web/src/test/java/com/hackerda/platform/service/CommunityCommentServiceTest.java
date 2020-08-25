package com.hackerda.platform.service;

import com.hackerda.platform.controller.vo.CommentDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("beta")
@SpringBootTest
public class CommunityCommentServiceTest {

    @Autowired
    private CommunityCommentService communityCommentService;

    @Test
    public void findByPostId() {

        CommentDetailVO commentDetailVO = communityCommentService.findByPostId("guest",1L);

        for (CommentDetailVO.Overview overview : commentDetailVO.getCommentList()) {
            System.out.println(overview);
        }


    }
}