package com.hackerda.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.ICaptchaProvider;
import com.hackerda.spider.exception.UrpException;
import com.hackerda.spider.predict.CaptchaPredict;
import com.hackerda.spider.support.UrpExamTime;
import com.hackerda.spider.support.UrpGeneralGrade;
import com.hackerda.spider.support.UrpStudentInfo;
import com.hackerda.spider.support.coursetimetable.UrpCourseTimeTable;
import com.hackerda.spider.support.scheme.Scheme;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestOperations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UrpCommonSpider extends UrpBaseSpider implements UrpSpider{

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


    public UrpCommonSpider(RestOperations client, CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider) {
        super(client, captchaPredict, captchaProvider);
    }

    public UrpCommonSpider(RestOperations client, CaptchaPredict captchaPredict, ICaptchaProvider<CaptchaImage> captchaProvider, IExceptionHandler exceptionHandler) {
        super(client, captchaPredict, captchaProvider, exceptionHandler);
    }


    public void login(String account, String password) {
        this.account = account;

        synchronized (account.intern()){
            if(cookiePersist.getByAccount(account) == null){
                super.login(account, password);
            }
        }
    }


    public UrpStudentInfo getStudentInfo() {

        String content = getContent(STUDENT_INFO);

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

        String result = getContent(CURRENT_TERM_GRADE);
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

        String content = getContent(SCHEME_GRADE);
        TypeReference<List<Scheme>> typeReference = new TypeReference<List<Scheme>>() {
        };
        return parseObject(content, typeReference);

    }

    public UrpCourseTimeTable getUrpCourseTimeTable() {

        String result = getContent(COURSE_TIME_TABLE);
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

        String s = getContent(EXAM_TIME);

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

    }
}
