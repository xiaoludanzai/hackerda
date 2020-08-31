package com.hackerda.platform.application.event;

import com.hackerda.platform.domain.community.LikeBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddLikeEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    private final LikeBO likeBO;
    public AddLikeEvent(Object source, LikeBO likeBO) {
        super(source);
        this.likeBO = likeBO;
    }
}
