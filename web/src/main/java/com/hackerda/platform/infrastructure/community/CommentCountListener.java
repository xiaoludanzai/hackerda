package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.application.event.CommentEvent;
import com.hackerda.platform.domain.community.CommentCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class CommentCountListener implements ApplicationListener<CommentEvent> {

    @Autowired
    private CommentCountService commentCountService;

    @Override
    public void onApplicationEvent(CommentEvent event) {

        if (event.isAdd()) {
            commentCountService.increment(event.getCommentBO().getPostId());
        }else {
            commentCountService.decrement(event.getCommentBO().getPostId());
        }
    }
}
