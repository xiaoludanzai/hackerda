package com.hackerda.platform.task;

import com.hackerda.platform.service.CourseSubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
