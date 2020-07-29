package com.hackerda.platform.task;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.builder.TemplateBuilder;
import com.hackerda.platform.config.wechat.WechatTemplateProperties;
import com.hackerda.platform.infrastructure.dao.StudentUserDao;
import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.constant.MiniProgram;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import com.hackerda.platform.service.CourseSubscribeService;
import com.hackerda.platform.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuki
 * @date 2018/11/6 20:36
 */
@Slf4j
@Service
public class CourseSubscriptionTask extends BaseSubscriptionTask {

    @Value("${scheduled.sendCourse}")
    private String updateSwitch;


    @Scheduled(cron = "0 0 8 * * ?")
//这个cron表达式的意思是星期一到星期五的早上8点执行一次
    void sendCourseRemindMsgForFirstSection() {
        execute(CourseSubscribeService.FIRST_SECTION);
    }

    @Scheduled(cron = "0 50 9 * * ?")
//这个cron表达式的意思是星期一到星期五的早上9点50分执行一次
    void sendCourseRemindMsgForSecondSection() {
        execute(CourseSubscribeService.SECOND_SECTION);
    }

    @Scheduled(cron = "0 0 13 * * ?")
//这个cron表达式的意思是星期一到星期五的下午13点执行一次
    void sendCourseRemindMsgForThirdSection() {
        execute(CourseSubscribeService.THIRD_SECTION);
    }

    @Scheduled(cron = "0 50 14 * * ?")
//这个cron表达式的意思是星期一到星期五的下午14点50分执行一次
    void sendCourseRemindMsgForFourthSection() {
        execute(CourseSubscribeService.FOURTH_SECTION);
    }

    @Scheduled(cron = "0 0 18 * * ?")
//这个cron表达式的意思是星期一到星期五的晚上6点执行一次
    void sendCourseRemindMsgForFifthSection() {
        execute(CourseSubscribeService.FIFTH_SECTION);
    }

    public void execute(int section) {
        log.info("send course message task switch {}", updateSwitch);

        if (!BooleanUtils.toBoolean(updateSwitch)) {
            return;
        }

//        List<WechatOpenid> openidList = courseSubscribeService.getSubscribeOpenid();
//        SchoolTime schoolTime = DateUtils.getCurrentSchoolTime();
//        for (WechatOpenid openid : openidList) {
//            courseSubscriptionSendPool.submit(() ->{
//                processOpenid(openid, section, schoolTime);
//            });
//
//        }

    }



}
