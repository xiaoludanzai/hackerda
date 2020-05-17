package com.hackerda.platform.service.wechat.handler.messageHandler;


import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.builder.TextBuilder;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.service.OpenIdService;
import com.hackerda.platform.service.TeachingEvaluationService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class EvaluationHandler implements WxMpMessageHandler {
    @Resource
    private OpenIdService openIdService;
    @Resource
    private TeachingEvaluationService teachingEvaluationService;
    @Resource
    private TextBuilder textBuilder;
    private static ExecutorService autoEvaluatePool = new MDCThreadPool(1, 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "evaluate"));


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        String appId = wxMpService.getWxMpConfigStorage().getAppId();

        String appid = wxMpService.getWxMpConfigStorage().getAppId();
        if(!(openIdService.openidIsBind(wxMpXmlMessage.getFromUser(), appid))){
            return textBuilder.build(teachingEvaluationService.getEvaluationLink(), wxMpXmlMessage, wxMpService);
        }

        StudentUser student = openIdService.getStudentByOpenId(wxMpXmlMessage.getFromUser(), appId);

        String account = student.getAccount().toString();
        String content;

        if (teachingEvaluationService.hasEvaluate(account)) {
            content = "您的评估已经完成评估啦！~";
        } else {
            if (teachingEvaluationService.isWaitingEvaluate(account)) {
                content = "稍待片刻，已经在队列中了，评估完成后立刻给你发通知提醒你";
            } else {
                content = "我们很快会为你完成评估，评估完成后立刻给你发通知提醒你";
                autoEvaluatePool.submit(() ->{
                    try {
                        while (teachingEvaluationService.evaluate(student) != 0){

                        }
                        teachingEvaluationService.sendMessageToOpenId(wxMpXmlMessage.getFromUser(), "久等了,评估已完成");
                        teachingEvaluationService.addFinishEvaluateAccount(account);
                    }catch (PasswordUnCorrectException e){
                        teachingEvaluationService.sendMessageToOpenId(wxMpXmlMessage.getFromUser(), "密码错误请重试");
                    }catch (Exception e) {
                        teachingEvaluationService.sendMessageToOpenId(wxMpXmlMessage.getFromUser(), "评估过程有些问题，请重试");
                    }
                });
            }
        }

        return textBuilder.build(content, wxMpXmlMessage, wxMpService);
    }
}
