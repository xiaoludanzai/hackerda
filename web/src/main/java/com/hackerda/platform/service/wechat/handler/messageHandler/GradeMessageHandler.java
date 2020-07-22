package com.hackerda.platform.service.wechat.handler.messageHandler;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.builder.TextBuilder;
import com.hackerda.platform.service.OpenIdService;
import com.hackerda.platform.service.ScheduleTaskService;
import com.hackerda.spider.exception.UrpEvaluationException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuki
 * @date 2018/7/15 15:31
 */
@Slf4j
@Component
public class GradeMessageHandler implements WxMpMessageHandler {

    @Resource
    private OpenIdService openIdService;
    @Resource
    private ScheduleTaskService scheduleTaskService;
    @Resource
    private TextBuilder textBuilder;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> map,
                                    WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) {

        return null;


    }

}