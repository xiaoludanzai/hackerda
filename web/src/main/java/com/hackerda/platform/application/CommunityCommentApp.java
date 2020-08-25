package com.hackerda.platform.application;

import com.hackerda.platform.domain.community.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CommunityCommentApp {

    @Autowired
    private ContentSecurityCheckService contentSecurityCheckService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;


    public void addComment(CommentBO commentBO) {

        if(!contentSecurityCheckService.isSecurityContent(commentBO.getContent())) {
            commentBO.setStatus(RecordStatus.UnderReview);
        }else {
            commentBO.setStatus(RecordStatus.Release);
        }

        commentRepository.save(commentBO);
    }

    public void addLike(LikeBO likeBO) {
        LikeBO like = likeRepository.find(likeBO.getUserName(), likeBO.getLikeType(), likeBO.getTypeContentId());

        if(like != null) {
            like.setShow(likeBO.isShow());
            like.setLikeTime(likeBO.getLikeTime());
            likeRepository.update(like);
        }else if(likeBO.isShow()){
            likeRepository.save(likeBO);
        }
    }


}
