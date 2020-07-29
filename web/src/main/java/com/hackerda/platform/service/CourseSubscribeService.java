package com.hackerda.platform.service;

import com.hackerda.platform.config.wechat.WechatMpPlusProperties;
import com.hackerda.platform.infrastructure.dao.WechatOpenIdDao;
import com.hackerda.platform.pojo.ScheduleTask;
import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.constant.SubscribeScene;
import com.hackerda.platform.pojo.vo.CourseTimeTableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuki
 * @date 2018/12/5 19:26
 */
@Slf4j
@Service
public class CourseSubscribeService {

    /**
     * 第一节
     */
    public final static int FIRST_SECTION = 1;
    /**
     * 第二节
     */
    public final static int SECOND_SECTION = 3;
    /**
     * 第三节
     */
    public final static int THIRD_SECTION = 5;
    /**
     * 第四节
     */
    public final static int FOURTH_SECTION = 7;
    /**
     * 第五节
     */
    public final static int FIFTH_SECTION = 9;


}
