package com.hackerda.platform.controller.wechat;

import com.hackerda.platform.config.wechat.WechatMpConfiguration;
import com.hackerda.platform.pojo.Student;
import com.hackerda.platform.pojo.constant.SubscribeScene;
import com.hackerda.platform.pojo.wechat.OneOffSubscription;
import com.hackerda.platform.service.CourseTimeTableService;
import com.hackerda.platform.service.wechat.StudentBindService;
import com.hackerda.platform.utils.OneOffSubcriptionUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author junrong.chen
 * @date 2018/10/7
 */
@Controller
@RequestMapping("/wechat/sub/{appid}")
@Slf4j
public class WxSubscriptionController {

}
