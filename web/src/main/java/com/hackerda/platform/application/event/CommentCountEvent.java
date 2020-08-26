package com.hackerda.platform.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentCountEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    private final int count;
    private final long postId;
    public CommentCountEvent(Object source, int count, long postId) {
        super(source);
        this.count = count;
        this.postId = postId;
    }
}
