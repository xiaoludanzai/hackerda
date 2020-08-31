package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.application.event.AddLikeEvent;
import com.hackerda.platform.domain.community.LikeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class LikeCountListener implements ApplicationListener<AddLikeEvent> {

    @Autowired
    private LikeCountService likeCountService;


    @Override
    public void onApplicationEvent(AddLikeEvent event) {

        if(event.getLikeBO().isShow()) {
            likeCountService.increment(event.getLikeBO().getLikeType(), event.getLikeBO().getTypeContentId(), event.getLikeBO().getUserName());

        } else {
            likeCountService.decrement(event.getLikeBO().getLikeType(), event.getLikeBO().getTypeContentId(),
                    event.getLikeBO().getUserName());
        }

    }
}
