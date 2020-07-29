package com.hackerda.platform.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hackerda.platform.pojo.Course;
import com.hackerda.platform.pojo.constant.RedisKeys;
import com.hackerda.platform.spider.model.VerifyCode;
import com.hackerda.platform.spider.newmodel.SearchResult;
import com.hackerda.platform.spider.newmodel.course.UrpCourseForSpider;
import com.hackerda.platform.spider.newmodel.emptyroom.EmptyRoomPojo;
import com.hackerda.platform.spider.newmodel.emptyroom.EmptyRoomPost;
import com.hackerda.platform.spider.newmodel.evaluation.EvaluationPagePost;
import com.hackerda.platform.spider.newmodel.evaluation.EvaluationPost;
import com.hackerda.platform.spider.newmodel.evaluation.searchresult.TeachingEvaluation;
import com.hackerda.platform.spider.newmodel.examtime.UrpExamTime;
import com.hackerda.platform.spider.newmodel.searchclass.ClassInfoSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.CourseTimetableSearchResult;
import com.hackerda.platform.spider.newmodel.searchclass.SearchClassInfoPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomPost;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchClassroomResult;
import com.hackerda.platform.spider.newmodel.searchclassroom.SearchResultWrapper;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCoursePost;
import com.hackerda.platform.spider.newmodel.searchcourse.SearchCourseResult;
import com.hackerda.platform.spider.newmodel.searchteacher.SearchTeacherPost;
import com.hackerda.platform.spider.newmodel.searchteacher.SearchTeacherResult;
import com.hackerda.platform.utils.ApplicationUtil;
import com.hackerda.spider.exception.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.MDC;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author junrong.chen
 */
@Slf4j
public class NewUrpSpider {

    static {
        Thread produceThread1 = new Thread(new CaptchaProducer());
        Thread produceThread2 = new Thread(new CaptchaProducer());
        Thread cleanThread = new Thread(new CaptchaCleaner());

        produceThread1.setName("produceThread1");
        produceThread2.setName("produceThread2");
        produceThread1.start();
        produceThread2.start();
        cleanThread.start();
        try {
            stringRedisTemplate = ApplicationUtil.getBean("stringRedisTemplate");
        } catch (Exception e) {
            log.error("inject error ", e);
        }

    }

    private static final String ROOT = "http://xsurp.usth.edu.cn";
    /**
     * 验证码
     */
    private static final String CAPTCHA = ROOT + "/img/captcha.jpg";
    /**
     * 登录校验
     */
    private static final String CHECK = ROOT + "/j_spring_security_check";
    /**
     * 仅做header refer使用
     */
    private static final String LOGIN = ROOT + "/getStudentInfo";

    private static final String MAKE_UP_GRADE = ROOT + "/student/examinationManagement/examGrade/search";
    /**
     * 空教室查询
     */
    private static final String EMPTY_ROOM = ROOT + "/student/teachingResources/freeClassroomQuery/search";
    /**
     * 班级信息查询
     */
    private static final String CLASS_INFO = ROOT + "/student/teachingResources/classCurriculum/search";


    /**
     * 教室信息查询
     */
    private static final String CLASSROOM_INFO = ROOT + "/student/teachingResources/classroomCurriculum/search";
    /**
     * 教师信息查询
     */
    private static final String TEACHER_INFO = ROOT + "/student/teachingResources/teacherCurriculum/search";


    /**
     * 查询课程信息
     */
    private static final String COURSE_SEARCH = ROOT + "/student/teachingResources/courseCurriculum/search";



    /**
     * 教学评估 teachingEvaluation/teachingEvaluation/search
     */
    private static final String Teaching_Evaluation = ROOT + "/student/teachingEvaluation/teachingEvaluation/search";

    /**
     * 问卷提交
     */
    private static final String EVALUATION = ROOT + "/student/teachingEvaluation/teachingEvaluation/evaluation";

    /**
     * 问卷页面
     */
    private static final String EVALUATION_PAGE = ROOT + "/student/teachingEvaluation/teachingEvaluation" +
            "/evaluationPage";



    private static StringRedisTemplate stringRedisTemplate;


    private static final UrpCookieJar COOKIE_JAR = new UrpCookieJar();


    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .cookieJar(COOKIE_JAR)
            .retryOnConnectionFailure(true)
            .connectTimeout(5000L, TimeUnit.MILLISECONDS)
            .readTimeout(8000L, TimeUnit.MILLISECONDS)
            .addInterceptor(new RetryInterceptor(5))
            .followRedirects(false)
            .build();

    private final static Headers HEADERS = new Headers.Builder()
            .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
            .add("Host", "xsurp.usth.edu.cn")
            .add("Connection", "keep-alive")
            .add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .add("Upgrade-Insecure-Requests", "1")
            .add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
            .add("Cache-Control", "max-age=0")
            .add("Referer", ": http://xsurp.usth.edu.cn/login")
            .build();

    private String account;
    private String password;

    private final static BlockingQueue<PreLoadCaptcha> queue = new ArrayBlockingQueue<>(5);


    /**
     * @param account  学号
     * @param password 密码
     * @throws UrpRequestException 请求异常
     */
    public NewUrpSpider(String account, String password) {
        MDC.put("account", account);
        MDC.remove("preLoad");
        this.account = account;
        this.password = password;
        synchronized (this.account.intern()){
            if (hasLoginCookieCache(account)) {
                log.info("account {} has login", account);
                return;
            }
            PreLoadCaptcha preLoadCaptcha;
            VerifyCode verifyCode = null;
            log.info("account {} start login", account);
            while ((preLoadCaptcha = queue.poll()) != null) {
                if (!preLoadCaptcha.isExpire()) {
                    MDC.put("preLoad", preLoadCaptcha.preloadCookieId);
                    verifyCode = preLoadCaptcha.captcha;
                    break;
                }
            }
            if (verifyCode == null) {
                verifyCode = getCaptcha();
            }

            Base64.Encoder encoder = Base64.getEncoder();

            UUID uuid = UUID.randomUUID();
            HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();

            opsForHash.put(RedisKeys.CAPTCHA.getName(), uuid.toString(), encoder.encodeToString(verifyCode.getData().clone()));
            String code = CaptchaBreaker.getCode(uuid.toString());

            studentCheck(account, password, code, uuid.toString());

            opsForHash.delete(RedisKeys.CAPTCHA.getName(), uuid.toString());
        }

    }

    /**
     * 获得补考成绩
     */
    public List<Map<String, Object>> getMakeUpGrade() {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params.add("jxzxjh", "2019-2020-1-1")
                .add("ksbh", "2019-2020-1-1-01")
                .add("pageNum", "1")
                .add("pageSize", "10")
                .build();

        Request request = new Request.Builder()
                .url(MAKE_UP_GRADE)
                .headers(HEADERS)
                .post(body)
                .build();
        String result = getContent(request);
        return parseObject(result, new TypeReference<List<Map<String, Object>>>() {
        });
    }



    private static VerifyCode getCaptcha() {
        Request request = new Request.Builder()
                .url(CAPTCHA)
                .header("Referer", LOGIN)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .get()
                .build();

        return new VerifyCode(execute(request));

    }

    /**
     * 登陆校验
     */
    private void studentCheck(String account, String password, String captcha, String uuid) {


        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params.add("j_username", account)
                .add("j_password", password)
                .add("j_captcha", captcha)
                .build();

        Request request = new Request.Builder()
                .url(CHECK)
                .headers(HEADERS)
                .post(body)
                .build();

        Response response = getResponse(request);
        String location = response.header("Location");

        if (StringUtils.isEmpty(location)) {
            COOKIE_JAR.clearSession();
            throw new UrpRequestException(request.url().toString(), response.code(), response.message());
        } else if (location.contains("badCaptcha")) {
            COOKIE_JAR.clearSession();
            throw new UrpVerifyCodeException("captcha: " + captcha + " code uuid :" + uuid);
        } else if (location.contains("badCredentials")) {
            throw new PasswordUnCorrectException("account: " + account);
        } else if (location.contains("concurrentSessionExpired")) {
            COOKIE_JAR.clearSession();
            throw new UrpSessionExpiredException("account: " + account + "session expired");
        }

        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set(RedisKeys.URP_LOGIN_COOKIE.genKey(account), uuid, 25L, TimeUnit.MINUTES);

    }

    /**
     * 获取空教室信息
     *
     * @param emptyRoomPost 请求参数
     */
    public EmptyRoomPojo getEmptyRoom(EmptyRoomPost emptyRoomPost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params.add("weeks", emptyRoomPost.getWeeks())
                .add("executiveEducationPlanNumber", "2019-2020-1-1")
                .add("codeCampusListNumber", "01")
                .add("teaNum", emptyRoomPost.getTeaNum())
                .add("wSection", emptyRoomPost.getWSection())
                .add("pageNum", emptyRoomPost.getPageNum())
                .add("pageSize", emptyRoomPost.getPageSize())
                .build();
        Request request = new Request.Builder()
                .url(EMPTY_ROOM)
                .headers(HEADERS)
                .post(body)
                .build();
        String result = getContent(request);
        try {
            List<EmptyRoomPojo> pojo = JSON.parseObject(result, new TypeReference<List<EmptyRoomPojo>>() {
            });
            return pojo.get(0);
        } catch (JSONException e) {
            if (result.length() > 1000) {
                throw new UrpEvaluationException("account: " + account + " 未完成评估无法查成绩");
            }
            log.error("parse grade error {}", result, e);
            COOKIE_JAR.clearSession();
            throw new UrpSessionExpiredException("account: " + account + " session expired");
        }
    }


    public List<SearchResult<ClassInfoSearchResult>> getClassInfoSearchResult(SearchClassInfoPost searchClassInfoPost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params.add("param_value", searchClassInfoPost.getParamValue())
                .add("executiveEducationPlanNum", searchClassInfoPost.getExecutiveEducationPlanNum())
                .add("pageNum", searchClassInfoPost.getPageNum())
                .add("pageSize", searchClassInfoPost.getPageSize())
                .add("yearNum", searchClassInfoPost.getYearNum())
                .add("departmentNum", searchClassInfoPost.getDepartmentNum())
                .add("classNum", searchClassInfoPost.getClassNum())
                .build();

        Request request = new Request.Builder()
                .url(CLASS_INFO)
                .headers(HEADERS)
                .post(body)
                .build();
        String result = getContent(request);
        TypeReference<List<SearchResult<ClassInfoSearchResult>>> reference = new TypeReference<List<SearchResult<ClassInfoSearchResult>>>() {
        };

        return parseObject(result, reference);
    }


    /**
     * 教师信息列表
     *
     * @param searchTeacherPost
     * @return
     */
    public List<SearchResult<SearchTeacherResult>> searchTeacherInfo(SearchTeacherPost searchTeacherPost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params
                .add("executiveEducationPlanNumber", searchTeacherPost.getExecutiveEducationPlanNum())
                .add("departmentNum", searchTeacherPost.getDepartmentNum())
                .add("teacherName", searchTeacherPost.getTeacherName())
                .add("pageSize", searchTeacherPost.getPageSize())
                .add("pageNum", searchTeacherPost.getPageNum())
                .build();

        Request request = new Request.Builder()
                .url(TEACHER_INFO)
                .headers(HEADERS)
                .post(body)
                .build();

        TypeReference<List<SearchResult<SearchTeacherResult>>> reference = new TypeReference<List<SearchResult<SearchTeacherResult>>>() {
        };
        return parseObject(getContent(request), reference);
    }

    /**
     * 查询教室信息
     *
     * @return
     */
    public List<SearchResultWrapper<SearchClassroomResult>> searchClassroomInfo(SearchClassroomPost searchClassroomPost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params
                .add("executiveEducationPlanNumber", searchClassroomPost.getExecutiveEducationPlanNum())
                .add("campusNumber", searchClassroomPost.getCampusNumber())
                .add("teachingBuildingNumber", searchClassroomPost.getTeachingBuildingNumber())
                .add("classRoomNumber", searchClassroomPost.getClassRoomNumber())
                .add("pageSize", searchClassroomPost.getPageSize())
                .add("pageNum", searchClassroomPost.getPageNum())
                .build();

        Request request = new Request.Builder()
                .url(CLASSROOM_INFO)
                .headers(HEADERS)
                .post(body)
                .build();

        TypeReference<List<SearchResultWrapper<SearchClassroomResult>>> reference = new TypeReference<List<SearchResultWrapper<SearchClassroomResult>>>() {
        };
        return parseObject(getContent(request), reference);
    }

    /**
     * 查询课程信息
     *
     * @param searchCoursePost
     */
    public SearchResult<SearchCourseResult> searchCourseInfo(SearchCoursePost searchCoursePost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params
                .add("zxjxjhh", searchCoursePost.getExecutiveEducationPlanNum())
                .add("kkxsh", searchCoursePost.getAcademyCode())
                .add("kcm", searchCoursePost.getCourseName())
                .add("kch", searchCoursePost.getCourseNumber())
                .add("kxh", searchCoursePost.getCourseOrderNumber())
                .add("kclb", searchCoursePost.getCourseType())
                .add("pageSize", searchCoursePost.getPageSize())
                .add("pageNum", searchCoursePost.getPageNum())
                .build();

        Request request = new Request.Builder()
                .url(COURSE_SEARCH)
                .headers(HEADERS)
                .post(body)
                .build();

        TypeReference<SearchResult<SearchCourseResult>> typeReference = new TypeReference<SearchResult<SearchCourseResult>>() {
        };
        return parseObject(getContent(request), typeReference);
    }


    public TeachingEvaluation searchTeachingEvaluationInfo() {
        Request request = new Request.Builder()
                .url(Teaching_Evaluation)
                .headers(HEADERS)
                .build();

        return parseObject(getContent(request), TeachingEvaluation.class);
    }


    public String getEvaluationToken(EvaluationPagePost evaluationPagePost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params
                .add("evaluatedPeople", evaluationPagePost.getEvaluatedPeople())
                .add("evaluatedPeopleNumber", evaluationPagePost.getEvaluatedPeopleNumber())
                .add("questionnaireCode", evaluationPagePost.getQuestionnaireCode())
                .add("questionnaireName", evaluationPagePost.getQuestionnaireName())
                .add("evaluationContentNumber", evaluationPagePost.getEvaluationContentNumber())
                .add("evaluationContentContent", evaluationPagePost.getEvaluationContentContent())
                .build();

        Request request = new Request.Builder()
                .url(EVALUATION_PAGE)
                .headers(HEADERS)
                .post(body)
                .build();

        String s = getContent(request);

        Document document = Jsoup.parse(s);
        Element element = document.getElementById("tokenValue");

        return element.attr("value");
    }


    public void evaluation(EvaluationPost evaluationPost) {
        FormBody.Builder params = new FormBody.Builder();
        FormBody body = params
                .add("0000000050", evaluationPost.getFirst())
                .add("0000000051", evaluationPost.getSecond())
                .add("0000000052", evaluationPost.getThird())
                .add("0000000053", evaluationPost.getFourth())
                .add("zgpj", evaluationPost.getComment())
                .add("questionnaireCode", evaluationPost.getQuestionnaireCode())
                .add("evaluationContentNumber", evaluationPost.getEvaluationContentNumber())
                .add("evaluatedPeopleNumber", evaluationPost.getEvaluatedPeopleNumber())
                .add("tokenValue", evaluationPost.getTokenValue())
                .add("count", evaluationPost.getCount())
                .build();

        Request request = new Request.Builder()
                .url(EVALUATION)
                .headers(HEADERS)
                .post(body)
                .build();
        String s = getContent(request);


    }


    private <T> T parseObject(String text, TypeReference<T> type) {
        try {
            return JSON.parseObject(text, type);
        } catch (JSONException e) {
            throw new UrpException("json 解析异常", e);
        }

    }

    private <T> T parseObject(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (JSONException e) {
            throw new UrpException("json 解析异常", e);
        }

    }
    

    private static byte[] execute(Request request) {
        try (Response response = CLIENT.newCall(request).execute()) {
            if (isResponseFail(response)) {
                throw new UrpRequestException(request.url().toString(), response.code(), response.message());
            }
            ResponseBody body = response.body();
            if (body == null) {
                throw new UrpRequestException(request.url().toString(), response.code(),
                        "cause: response body is null");
            }
            return body.bytes();
        } catch (IOException e) {
            throw new UrpTimeoutException(request.url().toString(), e);
        }
    }


    private static String getContent(Request request) {
        String content = new String(execute(request), StandardCharsets.UTF_8);
        if (content.contains("invalidSession") || content.contains("login")) {
            COOKIE_JAR.clearSession();
            throw new UrpSessionExpiredException("session expired");
        } else if (content.contains("没有完成评估")) {
            throw new UrpEvaluationException("评估未完成无法查看个人信息");
        }
        return content;
    }


    private Response getResponse(Request request) {
        try (Response response = CLIENT.newCall(request).execute()) {
            if (isResponseFail(response)) {
                throw new UrpRequestException(request.url().toString(), response.code(), response.message());
            }
            return response;
        } catch (IOException e) {
            throw new UrpTimeoutException(request.url().toString(), e);
        }
    }

    private boolean hasLoginCookieCache(String account) {

        return BooleanUtils.toBoolean(stringRedisTemplate.hasKey(RedisKeys.URP_COOKIE.genKey(account)))
                && BooleanUtils.toBoolean(stringRedisTemplate.hasKey(RedisKeys.URP_LOGIN_COOKIE.genKey(account)));
    }

    /**
     * @param response 响应
     * @return 是否成功响应
     */
    private static boolean isResponseFail(Response response) {
        return response.body() == null ||
                (!response.isSuccessful() && !response.isRedirect());
    }



    private static class CaptchaProducer implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                log.debug("produce captcha thread start");

                try {

                    UUID uuid = UUID.randomUUID();
                    MDC.put("preLoad", uuid.toString());
                    VerifyCode captcha = getCaptcha();
                    PreLoadCaptcha preLoadCaptcha = new PreLoadCaptcha(captcha, uuid.toString(), new Date());
                    queue.put(preLoadCaptcha);
                } catch (Throwable e) {
                    log.error("preload captcha error {}", e.getMessage());
                } finally {
                    MDC.clear();
                    log.debug("captcha queue size {}", queue.size());
                }

            }
        }
    }

    private static class CaptchaCleaner implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(1000 * 60 * 20);
                } catch (Throwable e) {
                    log.error("clean preload captcha error", e);
                }
                log.debug("clean up thread start");
                queue.removeIf(PreLoadCaptcha::isExpire);
            }
        }
    }

    @Data
    private static class PreLoadCaptcha {
        private VerifyCode captcha;
        private String preloadCookieId;
        private Date createDate;

        PreLoadCaptcha(VerifyCode captcha, String preloadCookieId, Date createDate) {
            this.captcha = captcha;
            this.preloadCookieId = preloadCookieId;
            this.createDate = createDate;
        }

        boolean isExpire() {
            return System.currentTimeMillis() - createDate.getTime() > 1000 * 60 * 20;
        }
    }

}