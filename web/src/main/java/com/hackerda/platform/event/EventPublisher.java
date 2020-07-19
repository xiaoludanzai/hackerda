package com.hackerda.platform.event;

import com.hackerda.platform.repository.FetchScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    // 事件发布方法
    public void publishGradeFetchFinish(String account) {
        applicationContext.publishEvent(new FetchFinishEvent(this, account, FetchScene.EVER_GRADE));
    }

}
