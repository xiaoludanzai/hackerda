package com.hackerda.platform.service;

import com.hackerda.platform.config.wechat.WechatMpConfiguration;
import com.hackerda.platform.config.wechat.WechatMpPlusProperties;
import com.hackerda.platform.dao.StudentUserDao;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.constant.RedisKeys;
import com.hackerda.platform.spider.newmodel.evaluation.EvaluationPagePost;
import com.hackerda.platform.spider.newmodel.evaluation.EvaluationPost;
import com.hackerda.platform.spider.newmodel.evaluation.searchresult.TeachingEvaluation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeachingEvaluationService {
    @Resource
    private NewUrpSpiderService newUrpSpiderService;
    @Resource
    private WechatMpPlusProperties wechatMpPlusProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private StudentUserDao studentUserDao;

    private static final String TEXT_LINK = "<a href=\"%s\">%s</a>";


    public int evaluate(String account) {
        StudentUser student = studentUserDao.selectStudentByAccount(Integer.parseInt(account));
        return evaluate(student);
    }

    public int evaluate(StudentUser student) {
        List<EvaluationPagePost> postList;
        long l = System.currentTimeMillis();
        log.info("start evaluate {}", student);
        postList = getEvaluationPagePost(student);
        postList.forEach(pagePost -> {
            String token = newUrpSpiderService.getEvaluationToken(student, pagePost);
            EvaluationPost post = new EvaluationPost()
                    .setTokenValue(token)
                    .setEvaluatedPeopleNumber(pagePost.getEvaluatedPeopleNumber())
                    .setEvaluationContentNumber(pagePost.getEvaluationContentNumber())
                    .setQuestionnaireCode(pagePost.getQuestionnaireCode());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("sleep error", e);
            }
            newUrpSpiderService.evaluate(student, post);
        });

        log.info("finish evaluate {} in {}ms", student, System.currentTimeMillis() - l);
        return getEvaluationPagePost(student).size();
    }

    public List<EvaluationPagePost> getEvaluationPagePost(StudentUser student) {
        TeachingEvaluation teachingEvaluation = newUrpSpiderService.searchTeachingEvaluationInfo(student);
        return teachingEvaluation.getData().stream()
                .filter(x -> "否".equals(x.getIsEvaluated()))
                .map(x -> new EvaluationPagePost()
                        .setQuestionnaireCode(x.getQuestionnaire().getQuestionnaireNumber())
                        .setQuestionnaireName(x.getQuestionnaire().getQuestionnaireName())
                        .setEvaluationContentNumber(x.getId().getEvaluationContentNumber())
                        .setEvaluatedPeople(x.getEvaluatedPeople())
                        .setEvaluatedPeopleNumber(x.getId().getEvaluatedPeople())
                ).collect(Collectors.toList());

    }


    public boolean hasEvaluate(String account){
        return BooleanUtils.toBoolean(stringRedisTemplate.opsForSet().isMember(RedisKeys.FINISH_EVALUATION_SET.getName(), account));
    }

    public boolean isWaitingEvaluate(String account){
        return BooleanUtils.toBoolean(stringRedisTemplate.opsForSet().isMember(RedisKeys.WAITING_EVALUATION_SET.getName(), account));
    }

    public void addEvaluateAccount(String account){
        stringRedisTemplate.opsForSet().add(RedisKeys.WAITING_EVALUATION_SET.getName(), account);
    }

    public void addFinishEvaluateAccount(String account){
        stringRedisTemplate.opsForSet().add(RedisKeys.WAITING_EVALUATION_SET.getName(), account);
    }

    public String getEvaluationLink(){
        return getTextLink("https://open.weixin.qq.com/connect/oauth2/authorize?appid" +
                        "=wx541fd36e6b400648" +
                "&redirect_uri=https://platform.hackerda.com/platform/bind/evaluate&response_type=code&scope=snsapi_base&state=wx541fd36e6b400648",
                "使用一键订阅之前请先点击蓝字进行绑定"
                );

    }



    public void sendMessageToOpenId(String openid, String content){
        log.info("send message {} to openid {}", openid, content);
        WxMpService service = WechatMpConfiguration.getMpServices().get(wechatMpPlusProperties.getAppId());
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setContent(content);
        wxMpKefuMessage.setMsgType("text");
        wxMpKefuMessage.setToUser(openid);
        try {
            service.getKefuService().sendKefuMessage(wxMpKefuMessage);
            log.info("send openid {} info {}", openid, wxMpKefuMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }


    }

    public String getTextLink(String url, String content) {
        return String.format(TEXT_LINK, url, content);
    }


}
