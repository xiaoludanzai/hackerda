package com.hackerda.platform.application.event;


import com.hackerda.platform.infrastructure.repository.FetchStatusRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class FetchEventListener implements ApplicationListener<FetchFinishEvent> {

    @Autowired
    private FetchStatusRecorder fetchStatusRecorder;

    @Override
    public void onApplicationEvent(FetchFinishEvent fetchFinishEvent) {
        fetchStatusRecorder.recordFinish(fetchFinishEvent.getFetchScene(), fetchFinishEvent.getAccount());
    }
}
