package com.hackerda.platform.application.event;

import com.hackerda.platform.domain.community.CommentBO;
import com.hackerda.platform.domain.community.LikeBO;
import com.hackerda.platform.infrastructure.repository.FetchScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    public void publishGradeFetchFinish(String account) {
        applicationContext.publishEvent(new FetchFinishEvent(this, account, FetchScene.EVER_GRADE));
    }


    public void addCommentEvent(CommentBO commentBO, boolean add) {
        applicationContext.publishEvent(new CommentEvent(this, commentBO.getPostId(), add));
    }

    public void addLikeEvent(LikeBO likeBO) {
        applicationContext.publishEvent(new AddLikeEvent(this, likeBO));
    }


}
