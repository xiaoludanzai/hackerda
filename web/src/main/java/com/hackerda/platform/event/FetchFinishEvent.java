package com.hackerda.platform.event;

import com.hackerda.platform.repository.FetchScene;
import org.springframework.context.ApplicationEvent;

public class FetchFinishEvent extends ApplicationEvent {


    private final String account;

    private final FetchScene fetchScene;

    public FetchFinishEvent(Object source, String account, FetchScene fetchScene) {
        super(source);
        this.account = account;
        this.fetchScene = fetchScene;
    }

    public String getAccount() {
        return account;
    }

    public FetchScene getFetchScene() {
        return fetchScene;
    }

}
