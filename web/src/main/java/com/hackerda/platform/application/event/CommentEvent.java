package com.hackerda.platform.application.event;

import com.hackerda.platform.domain.community.CommentBO;
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

    private final CommentBO commentBO;
    private final boolean add;
    public CommentEvent(Object source, CommentBO commentBO, boolean add) {
        super(source);
        this.commentBO = commentBO;
        this.add = add;
    }
}
