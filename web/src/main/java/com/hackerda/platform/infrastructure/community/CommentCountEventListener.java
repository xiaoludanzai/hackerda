package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.application.event.CommentCountEvent;
import com.hackerda.platform.infrastructure.database.mapper.PostMapper;
import com.hackerda.platform.infrastructure.database.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommentCountEventListener implements ApplicationListener<CommentCountEvent> {

    @Autowired
    private PostMapper postMapper;

    private final Map<Long, Integer> postCountMap = new ConcurrentHashMap<>();


    @Override
    public void onApplicationEvent( CommentCountEvent event) {

        Integer count = postCountMap.get(event.getPostId());

        if(count == null) {
            postCountMap.put(event.getPostId(), event.getCount());
            Post post = new Post();
            post.setId(event.getPostId());
            post.setCommentCount(event.getCount());
            postMapper.updateByPrimaryKeySelective(post);
        } else if(event.getCount() != count) {
            Post post = new Post();
            post.setId(event.getPostId());
            post.setCommentCount(event.getCount());
            postMapper.updateByPrimaryKeySelective(post);
        }

    }
}
