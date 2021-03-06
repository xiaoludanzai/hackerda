package com.hackerda.platform.config.wechat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.service.wechat.interceptor.StudentInfoInterceptor;
import com.hackerda.platform.service.wechat.interceptor.WechatOpenIdInterceptor;
import com.hackerda.platform.service.wechat.WxMessageRouter;
import com.hackerda.platform.service.wechat.handler.messageHandler.*;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@ComponentScan(basePackages = "com.hackerda.platform.config.*")
@EnableConfigurationProperties(value = {WechatMpProProperties.class, WechatMpPlusProperties.class, WechatTemplateProperties.class})
public class WechatMpConfiguration {

    @Resource
    private WechatMpProProperties wechatMpProProperties;

    @Resource
    private WechatMpPlusProperties wechatMpPlusProperties;

    @Resource
    private MiniProgramProperties miniProgramProperties;

    @Resource
    private CourseMessageHandler courseMessageHandler;

    @Resource
    private MakeUpGradeHandler makeUpGradeHandler;

    @Resource
    private GradeMessageHandler gradeMessageHandler;

    @Resource
    private OpenidMessageHandler openidMessageHandler;

    @Resource
    private UnbindMessageHandler unbindMessageHandler;

    @Resource
    private EmptyRoomHandler emptyRoomHandler;

    @Resource
    private ExamMessageHandler examMessageHandler;

    @Resource
    private WechatOpenIdInterceptor wechatOpenIdInterceptor;

    @Resource
    private UnsubscribeMessageHandler unsubscribeMessageHandler;

    @Resource
    private SubscribeMessageHandler subscribeMessageHandler;

    @Resource
    private StudentInfoInterceptor studentInfoInterceptor;


    @Resource
    private CourseRankHandler courseRankHandler;

    @Resource
    private SubscribeEventHandler subscribeEventHandler;

    @Resource
    private EvaluationHandler evaluationHandler;

    private static final Map<String, WxMpMessageRouter> routers = Maps.newHashMap();
    private static final Map<String, WxMpService> mpServices = Maps.newHashMap();

    @Bean
    public WxMpService wxPlusService() {
        WxMpInMemoryConfigStorage plusConfig = wechatMpPlusProperties.getWxMpInMemoryConfigStorage();
        WxMpService wxPlusMpService = new WxMpServiceImpl();
        wxPlusMpService.setWxMpConfigStorage(plusConfig);
        return wxPlusMpService;
    }

    @Bean
    public WxMpService wxProService() {
        WxMpInMemoryConfigStorage proConfig = wechatMpProProperties.getWxMpInMemoryConfigStorage();
        WxMpService wxProMpService = new WxMpServiceImpl();
        wxProMpService.setWxMpConfigStorage(proConfig);
        return wxProMpService;
    }

    @Bean
    public Object services(WxMpService wxPlusService, WxMpService wxProService) {
        //plus的配置

        routers.put(wechatMpPlusProperties.getAppId(), this.newRouter(wxPlusService));
        mpServices.put(wechatMpPlusProperties.getAppId(), wxPlusService);

        //pro的配置

        routers.put(wechatMpProProperties.getAppId(), this.newRouter(wxProService));
        mpServices.put(wechatMpProProperties.getAppId(), wxProService);
        return Boolean.TRUE;
    }

    @Bean
    public Map<String , WechatPlatform> wechatPlatformMap(){

        ImmutableMap.Builder<String, WechatPlatform> builder = ImmutableMap.builder();

        return builder.put(wechatMpPlusProperties.getAppId(), WechatPlatform.HKXJ_PLUS)
                .put(wechatMpProProperties.getAppId(), WechatPlatform.HKXJ_PRO)
                .put(miniProgramProperties.getAppId(), WechatPlatform.HKXJ_APP)
                .put("test_appId", WechatPlatform.TEST)
                .build();

    }


    private WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMessageRouter newRouter = new WxMessageRouter(wxMpService);
        newRouter
                .rule()
                .async(false)
                .rContent("(课表|课程|今日课表)")
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .handler(courseMessageHandler)
                .end()
                .rule()
                .async(false)
                .rContent("订阅|课表推送|成绩推送|考试推送")
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .handler(subscribeMessageHandler)
                .end()
                .rule()
                .async(true)
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .rContent("补考成绩.*?")
                .handler(makeUpGradeHandler)
                .end()
                .rule()
                .async(true)
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .rContent(".*?成绩.*?")
                .handler(gradeMessageHandler)
                .end()

                .rule()
                    .async(false)
                    .interceptor(wechatOpenIdInterceptor)
                    .content("openid")
                    .handler(openidMessageHandler)
                    .end()
                .rule()
                .async(false)
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .rContent("解绑|解除绑定")
                .handler(unbindMessageHandler)
                .end()
                .rule()
                .async(true)
                .rContent(".*?考试.*?")
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .handler(examMessageHandler)
                .end()
                .rule()
                .async(false)
                .rContent("空教室.*?")
                .handler(emptyRoomHandler)
                .end()
                .rule()
                .async(false)
                .rContent("退订.*?")
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .handler(unsubscribeMessageHandler)
                .async(false)
                .rContent("课时排行|课时安排|课时排名|课表排行|科时排行|科时排名")
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .handler(courseRankHandler)
                .end()

                .rule()
                .async(false)
                .rContent("配对")
                .interceptor(wechatOpenIdInterceptor)
                .interceptor(studentInfoInterceptor)
                .handler(courseMessageHandler)
                .end()

                .rule()
                .async(false)
                .event("subscribe")
                .handler(subscribeEventHandler)
                .end()


                .rule()
                .async(false)
                .event("CLICK")
                .eventKey("evaluation")
                .handler(evaluationHandler)
                .end();

        ;
        return newRouter;
    }


    public void setPlusMenu(){
        WxMpService service = getMpServices().get(wechatMpPlusProperties.getAppId());
        WxMenu menu = new WxMenu();

        List<WxMenuButton> buttons = new ArrayList<>();
//        WxMenuButton button1 = new WxMenuButton();
//        button1.setName("一键教评");
//        button1.setType("click");
//        button1.setKey("evaluation");

        WxMenuButton button2 = new WxMenuButton();
        button2.setName("用户绑定");
        button2.setType("view");
        button2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx541fd36e6b400648&redirect_uri" +
                "=https://platform.hackerda.com/platform/bind/menu&response_type=code&scope=snsapi_base&state=wx541fd36e6b400648");

        WxMenuButton button3 = new WxMenuButton();
        button3.setName("成绩&考试");
        button3.setType("miniprogram");
        button3.setAppId("wx05f7264e83fa40e9");
        button3.setPagePath("pages/index/index");
        button3.setUrl("http://mp.weixin.qq.com");

//        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);

        menu.setButtons(buttons);
        System.out.println(menu.toJson());
        try {
            WxMpMenuService menuService = service.getMenuService();

            menuService.menuCreate(menu);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, WxMpMessageRouter> getRouters() {
        return routers;
    }

    public static Map<String, WxMpService> getMpServices() {
        return mpServices;
    }

}
