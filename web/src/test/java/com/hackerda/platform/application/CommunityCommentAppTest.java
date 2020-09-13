package com.hackerda.platform.application;

import com.hackerda.platform.domain.community.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@ActiveProfiles("prod")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityCommentAppTest {

    @Autowired
    private CommunityCommentApp communityCommentApp;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private LikeCountService likeCountService;

    @Test
    public void addComment() {

        CommentBO commentBO = new CommentBO(1L, "2014025838", "2017025838", "测试评论", 1L, 1L,
                IdentityCategory.Community, "2014025838");
        CommentBO commentBO2 = new CommentBO(1L, "2014025838", "2017025838", "测试评论1", 2L, 2L,
                IdentityCategory.Community, "2014025838");
        communityCommentApp.addComment(commentBO);
        communityCommentApp.addComment(commentBO2);
        List<CommentDetailBO> postId = commentRepository.findDetailByPostId(1L);
        for (CommentDetailBO detailBO : postId) {
            System.out.println(detailBO);
        }

    }


    @Test
    public void addLike() {

        LikeBO likeBO = new LikeBO();
        likeBO.setLikeTime(new Date());
        likeBO.setUserName("2014025838");
        likeBO.setShow(false);
        likeBO.setTypeContentId(1L);
        likeBO.setLikeType(LikeType.Post);

        communityCommentApp.addLike(likeBO);

        LikeBO db = likeRepository.find("2014025838",  LikeType.Post, 1L);

    }

    @Test
    public void countSynchronize() {
        communityCommentApp.countSynchronize();
        long count = likeCountService.likeCount(LikeType.Post, 1);
        System.out.println(count);
    }
}