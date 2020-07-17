package com.hackerda.platform.domain.grade;

import com.hackerda.platform.builder.TemplateBuilder;
import com.hackerda.platform.config.wechat.WechatTemplateProperties;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.constant.MiniProgram;
import com.hackerda.platform.pojo.wechat.miniprogram.SubscribeGradeData;
import com.hackerda.platform.pojo.wechat.miniprogram.SubscribeMessage;
import com.hackerda.platform.pojo.wechat.miniprogram.SubscribeValue;
import com.hackerda.platform.service.SubscribeService;
import com.hackerda.platform.service.wechat.SendMessageService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GradeMsgSender {

    @Autowired
    private SendMessageService sendMessageService;
    @Resource
    private TemplateBuilder templateBuilder;
    @Resource
    private WechatTemplateProperties wechatTemplateProperties;




    public void sendMessageToApp(StudentUser student, GradeBO gradeVo){
        SubscribeMessage<SubscribeGradeData> appMessage = new SubscribeMessage<>();
        appMessage.setTemplateId("dmE0nyulM8OVcUs-KojDxCYECrKTmzOGDkEUUm2T5UE")
                .setPage(MiniProgram.GRADE_PATH.getValue())
                .setData(new SubscribeGradeData()
                        .setCourseName(new SubscribeValue(gradeVo.getCourse().getName()))
                        .setName(new SubscribeValue(student.getName()))
                        .setScore(new SubscribeValue(gradeVo.getScore().toString()))
                        .setRemark(new SubscribeValue("如需获取新提醒，请重新点击订阅~")));
        sendMessageService.sendAppTemplateMessage(appMessage, student.getAccount());

    }

    public void sendMessageToPlus(StudentUser student, GradeBO gradeVo){
        List<WxMpTemplateData> templateData = templateBuilder.gradeToTemplateData(student, gradeVo);
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        miniProgram.setAppid(MiniProgram.APP_ID);
        miniProgram.setPagePath(MiniProgram.GRADE_PATH.getValue());
        WxMpTemplateMessage templateMessage =
                templateBuilder.build("", templateData, wechatTemplateProperties.getPlusGradeUpdateTemplateId(),
                        miniProgram);

        sendMessageService.sendPlusTemplateMessageByAccount(templateMessage, student.getAccount());


    }

    public void sendUpdateGradeToStudent(StudentUser student, List<GradeBO> sendGrade) {

        for (GradeBO gradeBO : sendGrade) {
            sendMessageToApp(student, gradeBO);
            sendMessageToPlus(student, gradeBO);
        }
    }
}
