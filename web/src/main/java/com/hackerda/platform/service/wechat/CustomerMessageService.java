package com.hackerda.platform.service.wechat;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.concurrent.*;

/**
 * @author JR Chan
 * @date 2019/5/3
 */
@Slf4j
@Service
@NoArgsConstructor
public class CustomerMessageService {


    private static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");


    private static ThreadFactory gradeUpdateThreadFactory = new ThreadFactoryBuilder().setNameFormat("grade-notice-%d").build();
    private static ExecutorService gradeUpdateNotice = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), gradeUpdateThreadFactory);

    private WxMpXmlMessage wxMpXmlMessage;
    private WxMpService wxMpService;


    public CustomerMessageService(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService) {
        this.wxMpService = wxMpService;
        this.wxMpXmlMessage = wxMpXmlMessage;
    }


    void sendMessage(WxMpXmlOutMessage message) {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setToUser(wxMpXmlMessage.getFromUser());
        if(message.getMsgType().equals(WxConsts.MassMsgType.TEXT)){
            WxMpXmlOutTextMessage textMessage = (WxMpXmlOutTextMessage) message;
            wxMpKefuMessage.setContent(textMessage.getContent());
            wxMpKefuMessage.setMsgType("text");
            log.info("send customer message {}", textMessage.getContent());
        }else {
            throw new RuntimeException("unSupport message type");
        }

        try {

            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        } catch (WxErrorException e) {
            log.error("send customer message error", e);
        }
    }




}
