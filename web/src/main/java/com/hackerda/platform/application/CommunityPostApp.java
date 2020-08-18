package com.hackerda.platform.application;

import com.hackerda.platform.domain.community.ContentSecurityCheckService;
import com.hackerda.platform.domain.community.PostBO;
import com.hackerda.platform.domain.community.PosterRepository;
import com.hackerda.platform.domain.community.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityPostApp {

    @Autowired
    private ContentSecurityCheckService contentSecurityCheckService;
    @Autowired
    private PosterRepository posterRepository;

    public void createPost(PostBO postBO) {

        if(!contentSecurityCheckService.checkMsg(postBO.getContent())) {
            postBO.setStatus(RecordStatus.UnderReview);
        }else {
            postBO.setStatus(RecordStatus.Release);
        }

        posterRepository.save(postBO);

    }

}
