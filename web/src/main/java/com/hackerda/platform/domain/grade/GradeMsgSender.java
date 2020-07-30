package com.hackerda.platform.domain.grade;

import com.hackerda.platform.builder.TemplateBuilder;
import com.hackerda.platform.config.wechat.WechatMpConfiguration;
import com.hackerda.platform.config.wechat.WechatTemplateProperties;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatOpenidBO;
import com.hackerda.platform.domain.constant.MiniProgram;
import com.hackerda.platform.service.wechat.miniprogram.SubscribeGradeData;
import com.hackerda.platform.service.wechat.miniprogram.SubscribeMessage;
import com.hackerda.platform.service.wechat.miniprogram.SubscribeValue;
import com.hackerda.platform.service.wechat.MiniProgramService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class GradeMsgSender {

    @Resource
    private TemplateBuilder templateBuilder;
    @Resource
    private WechatTemplateProperties wechatTemplateProperties;
    @Autowired
    private MiniProgramService miniProgramService;

    private void sendMessageToApp(StudentUserBO student, GradeBO gradeVo){
        if(student.hasBindApp()){
            SubscribeMessage<SubscribeGradeData> appMessage = new SubscribeMessage<>();
            appMessage.setTemplateId("dmE0nyulM8OVcUs-KojDxCYECrKTmzOGDkEUUm2T5UE")
                    .setPage(MiniProgram.GRADE_PATH.getValue())
                    .setToUser(student.getAppOpenid().getOpenid())
                    .setData(new SubscribeGradeData()
                            .setCourseName(new SubscribeValue(gradeVo.getCourse().getName()))
                            .setName(new SubscribeValue(student.getName()))
                            .setScore(new SubscribeValue(gradeVo.getScore().toString()))
                            .setRemark(new SubscribeValue("如需获取新提醒，请重新点击订阅~")));
            miniProgramService.sendSubscribeMessage(appMessage);
        }

    }

    private void sendMessageToPlus(StudentUserBO student, GradeBO gradeVo){
        if(student.hasBindPlus()){
            WechatOpenidBO openid = student.getPlusOpenid();
            List<WxMpTemplateData> templateData = templateBuilder.gradeToTemplateData(student, gradeVo);
            WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
            miniProgram.setAppid(MiniProgram.APP_ID);
            miniProgram.setPagePath(MiniProgram.GRADE_PATH.getValue());
            WxMpTemplateMessage templateMessage =
                    templateBuilder.build(openid.getOpenid(), templateData,
                            wechatTemplateProperties.getPlusGradeUpdateTemplateId(),
                            miniProgram);

            sendTemplateMessage(openid.getAppId(), templateMessage);
        }
    }

    private void sendTemplateMessage(String appId, WxMpTemplateMessage templateMessage){
        WxMpService wxMpService = WechatMpConfiguration.getMpServices().get(appId);
        try {
            log.info("send template message {}", templateMessage.getData());
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("send template message error", e);
        }
    }

    public void sendUpdateGradeToStudent(StudentUserBO student, List<GradeBO> sendGrade) {
        for (GradeBO gradeBO : sendGrade) {
            sendMessageToApp(student, gradeBO);
            sendMessageToPlus(student, gradeBO);
        }
    }
}
