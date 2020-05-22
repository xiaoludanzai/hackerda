package com.hackerda.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hackerda.spider.captcha.*;
import com.hackerda.spider.client.AccountRestTemplate;
import com.hackerda.spider.client.UrpRestTemplate;
import com.hackerda.spider.exception.*;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.predict.SchoolCaptchaPredictor;
import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
public class UrpBaseSpider implements UrpSpider {

    private final String ROOT = "http://xsurp.usth.edu.cn";
    /**
     * 登录校验
     */
    private final String CHECK = ROOT + "/j_spring_security_check";
    /**
     * 学生基本信息
     */
    private final String STUDENT_INFO = ROOT + "/student/rollManagement/rollInfo/index";

    /**
     * 当前学期成绩
     */
    private final String CURRENT_TERM_GRADE = ROOT + "/student/integratedQuery/scoreQuery/thisTermScores/data";

    /**
     * 方案成绩页面  包含所有的成绩
     * 选用这个地址的原因是因为，他在所有这些不规范的接口中算是比较规范的一个了
     */
    private final String SCHEME_GRADE = ROOT + "/student/integratedQuery/scoreQuery/schemeScores/callback";

    private final String COURSE_TIME_TABLE = ROOT + "/student/courseSelect/thisSemesterCurriculum/ajaxStudentSchedule/callback";
    private final String EXAM_TIME = ROOT + "/student/examinationManagement/examPlan/index";

    private static final Splitter SPACE_SPLITTER = Splitter.on(" ").omitEmptyStrings().trimResults();


    private final AccountRestTemplate<String> client;
    private final CaptchaPredict captchaPredict;
    private final ICaptchaProvider<CaptchaImage> captchaProvider;
    private final IExceptionHandler exceptionHandler;
    private String account;


    public UrpBaseSpider(AccountRestTemplate<String> client, CaptchaPredict captchaPredict,
                         ICaptchaProvider<CaptchaImage> captchaProvider) {
        this(client, captchaPredict, captchaProvider , null);
    }

    public UrpBaseSpider(AccountRestTemplate<String> client, CaptchaPredict captchaPredict,
                         ICaptchaProvider<CaptchaImage> captchaProvider, IExceptionHandler exceptionHandler) {
        this.client = client;
        this.captchaPredict = captchaPredict;
        this.captchaProvider = captchaProvider;
        this.exceptionHandler = exceptionHandler;
    }


    public void login(String account, String password) {
        this.account = account;

        CaptchaImage preLoad = captchaProvider.get();
        String predict = captchaPredict.predict(preLoad);

        client.setAccount(account);
        client.setCookies(preLoad.getCookie());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("j_username", account);
        map.add("j_password", password);
        map.add("j_captcha", predict);

        ResponseEntity<String> data = postFormData(map, CHECK, String.class);


        if (data.getStatusCode().is3xxRedirection()) {
            List<String> locationList = data.getHeaders().get(HttpHeaders.LOCATION);
            if (locationList != null && !locationList.isEmpty()) {
                String location = locationList.get(0);
                if (StringUtils.isEmpty(location)) {
                    cleanLoginInfo();
                    throw new UrpRequestException(CHECK, data.getStatusCodeValue(), data.getBody());
                }
            }

        }

        checkResult(data.getBody());
    }

    public UrpStudentInfo getStudentInfo() {

        String content = getContent(STUDENT_INFO, null);

        Map<String, String> userInfo = parseUserInfo(content);

        UrpStudentInfo student = new UrpStudentInfo();
        student.setAccount(Integer.parseInt(account));
        student.setEthnic(userInfo.get("民族"));
        student.setSex(userInfo.get("性别"));
        student.setName(userInfo.get("姓名"));
        student.setMajor(userInfo.get("专业"));
        student.setAcademy(userInfo.get("院系"));
        String s = userInfo.get("班级");
        student.setClassname(s.endsWith("班") ? s.substring(0, s.length() - 1) : s);
        return student;
    }

    public List<UrpGeneralGrade> getCurrentGeneralGrade() {

        String result = getContent(CURRENT_TERM_GRADE, null);
        List<Map<String, Object>> list = parseObject(result, new TypeReference<List<Map<String, Object>>>() {
        });
        JSONArray jsonArray = (JSONArray) list.get(0).get("list");

        List<UrpGeneralGrade> grade = jsonArray.toJavaList(UrpGeneralGrade.class);

        grade.stream().findFirst().ifPresent(x -> {
            if (!account.equals(x.getId().getStudentNumber())) {
                log.error("date error. user account: {} return account {}", account,
                        x.getId().getStudentNumber());
                throw new UrpException(String.format("date error. user account: %s return account %s", account,
                        x.getId().getStudentNumber()));
            }
        });

        return grade;
    }

    public List<Scheme> getSchemeGrade() {

        String content = getContent(SCHEME_GRADE, null);
        TypeReference<List<Scheme>> typeReference = new TypeReference<List<Scheme>>() {
        };
        return parseObject(content, typeReference);

    }

    public UrpCourseTimeTable getUrpCourseTimeTable() {

        String result = getContent(COURSE_TIME_TABLE, null);
        String regex = "\"dateList\": [.*]}$";
        result = result.replaceAll(regex, "");


        UrpCourseTimeTable tableForSpider = parseObject(result, UrpCourseTimeTable.class);

        tableForSpider.getDetails().stream()
                .findFirst().flatMap(detail -> detail.entrySet().stream()
                .findFirst()).ifPresent(entry -> {
            String number = entry.getValue().getCourseRelativeInfo().getStudentNumber();
            if (!account.equals(number)) {
                log.error("data error  account:{} date:{}", this.account, tableForSpider);
                throw new UrpException(String.format("date error. user account: %s return account %s", account,
                        number));
            }
        });
        return tableForSpider;

    }

    public List<UrpExamTime> getExamTime() {

        String s = getContent(EXAM_TIME, null);

        Document document = Jsoup.parse(s);
        Elements elements = document.getElementsByClass("clearfix");
        List<UrpExamTime> result = Lists.newArrayListWithExpectedSize(elements.size());
        for (Element element : elements) {
            List<String> list = SPACE_SPLITTER.splitToList(element.text());
            if (list.size() == 7) {
                result.add(new UrpExamTime()
                        .setCourseName(list.get(0))
                        .setDate("")
                        .setExamName(list.get(1)));
            }
            if (list.size() == 11) {
                result.add(new UrpExamTime()
                        .setCourseName(list.get(1))
                        .setExamName(list.get(2))
                        .setWeekOfTerm(list.get(4))
                        .setDate(list.get(5))
                        .setWeek(list.get(6))
                        .setExamTime(list.get(7))
                        .setLocation(list.get(8)));
            }

        }
        result.sort(Comparator.comparing(UrpExamTime::getDate));
        return result;
    }

    <T> ResponseEntity<T> postFormData(MultiValueMap<String, String> paramMap, String url,
                                       Class<T> resultType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramMap, headers);
        return client.postForEntity(url, request, resultType);

    }


    protected void checkResult(String content) {
        if (content != null) {
            RuntimeException exception = null;
            if (content.contains("badCaptcha")) {
                exception = new UrpVerifyCodeException("verify code error");
            } else if (content.contains("badCredentials")) {
                exception = new PasswordUnCorrectException("account: " + account);
            } else if (content.contains("invalidSession") || content.contains("login")) {
                exception = new UrpSessionExpiredException("session expired");
            } else if (content.contains("没有完成评估")) {
                exception = new UrpEvaluationException("评估未完成无法查看个人信息");
            }

            if (exception != null) {
                if(exceptionHandler != null){
                    exceptionHandler.handle(exception);
                }

                if(!(exception instanceof UrpEvaluationException)){
                    cleanLoginInfo();
                }

                throw exception;
            }

        }
    }

    protected String getContent(String url, Map<String, ?> uriVariables) {

        ResponseEntity<String> entity;

        if (uriVariables == null) {
            entity = client.getForEntity(url, String.class);
        } else {
            entity = client.getForEntity(url, String.class, uriVariables);
        }

        String content = entity.getBody();

        checkResult(content);


        return content;

    }


    /**
     * 解析学生信息页面的html
     */
    private Map<String, String> parseUserInfo(String html) {

        HashMap<String, String> infoMap = new HashMap<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("profile-info-row");
        for (Element e : elements) {
            Elements name = e.getElementsByClass("profile-info-name");
            List<Element> nameList = Lists.newArrayList(name.iterator());
            Elements value = e.getElementsByClass("profile-info-value");
            List<Element> valueList = Lists.newArrayList(value.iterator());

            for (int x = 0; x < nameList.size(); x++) {
                infoMap.put(nameList.get(x).text(), valueList.get(x).text());
            }
        }

        return infoMap;
    }

    protected void cleanLoginInfo() {
        client.setCookies(Collections.emptyList());
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

    public static void main(String[] args) {
        UrpRestTemplate<String> template = new UrpRestTemplate<>();
        RestTemplate commonClient = new RestTemplate();
        CaptchaPredict predictor = new SchoolCaptchaPredictor(commonClient, "http://127.0.0.1:10086/valid");
        ICaptchaProvider<CaptchaImage> provider =
                new PreloadCaptchaProvider(commonClient, "http://xsurp.usth.edu.cn/img/captcha" +
                        ".jpg");
        UrpBaseSpider spider = new UrpBaseSpider(template, predictor, provider);
        spider.login("account", "password");

    }
}
