package com.hackerda.platform.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    private final long postId;
    private final boolean add;
    public CommentEvent(Object source, long postId, boolean add) {
        super(source);
        this.postId = postId;
        this.add = add;
    }
}
