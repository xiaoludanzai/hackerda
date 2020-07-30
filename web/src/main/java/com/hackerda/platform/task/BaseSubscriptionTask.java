package com.hackerda.platform.task;

import com.hackerda.platform.config.wechat.WechatMpConfiguration;
import com.hackerda.platform.infrastructure.database.model.WechatOpenid;
import com.hackerda.platform.service.ScheduleTaskService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yuki
 * @date 2019/10/16 10:20
 *
 * 希望通过这个类来为定时任务提供消息发送的函数
 */
@Slf4j
@Service
public class BaseSubscriptionTask {

    @Resource
    private ScheduleTaskService scheduleTaskService;

    /**
     * 为定时任务提供发送模板消息的模板函数
     * @param templateMessage 模板消息
     * @return 消息是否发送成功
     */
    protected boolean sendTemplateMessage(WxMpTemplateMessage templateMessage,
                                          WechatOpenid openid, String logTitle){
        WxMpService wxMpService = WechatMpConfiguration.getMpServices().get(openid.getAppid());

        try {
            //发送成功的同时更新发送状态
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

            log.info("send {} Message to appid:{} openid:{} message:{} success", logTitle,
                    wxMpService.getWxMpConfigStorage().getAppId(), openid, templateMessage.getData());
            return true;
        } catch (WxErrorException e) {
            log.error("send {} Message to appid:{} openid:{} failed", logTitle,
                    wxMpService.getWxMpConfigStorage().getAppId(), openid, e);
            return false;
        }
    }

}
