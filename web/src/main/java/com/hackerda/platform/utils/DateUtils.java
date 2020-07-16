package com.hackerda.platform.utils;

import com.hackerda.platform.pojo.SchoolTime;
import com.hackerda.platform.pojo.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Yuki
 * @date 2018/11/5 23:22
 */

@Component
@Slf4j
public class DateUtils {

    private final static String term_start = "2020-03-02";

    public final static String YYYY_MM_DD_PATTERN = "yyyyMMdd";

    public final static String PATTERN_WITHOUT_SPILT = "yyyyMMddHHmmSS";

    public final static String YYYY_MM_PATTERN = "yyyyMM";

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd";

    public final static String HH_MM_SS_PATTERN = "hh:mm:ss";


    public static Integer getCurrentWeek() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        try {
            calendar.setTime(format.parse(term_start));
        } catch (ParseException e) {
            log.error("parse string to date fail，error message{}", e.getMessage());
            throw new RuntimeException("parse string to date fail，error message" + e.getMessage());
        }
        long start = calendar.getTimeInMillis();
        long end = Calendar.getInstance().getTimeInMillis();
        int result = (int) Math.ceil(((end - start) / 1000 / 60 / 60 / 24 / 7)) + 1;
        return Math.max(result, 1);
    }

    /**
     * 获取当天是周几
     *
     * @return 星期几
     */
    public static Integer getCurrentDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //星期天是1，星期一是2，星期二是3，星期三是4，星期四是5，星期五是6，星期六是7
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (currentDay < 0) {
            currentDay = 0;
        }
        return currentDay;
    }

    public static String getTimeOfPattern(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    /**
     * 格式化时间转换为标准Java时间
     * @return
     */
    public static Date localDateToDate(String time, String pattern) {

        SimpleDateFormat format =  new SimpleDateFormat(pattern);

        try {
            return format.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static SchoolTime getCurrentSchoolTime() {
        SchoolTime schoolTime = new SchoolTime();
        schoolTime.setDay(getCurrentDay());
        schoolTime.setSchoolWeek(getCurrentWeek());
        schoolTime.setTerm(new Term(2019, 2020, 2));
        return schoolTime;
    }

    public static String dateToChinese(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        StringBuffer buffer = new StringBuffer();
        buffer.append(cal.get(Calendar.YEAR)).append("年")
                .append(cal.get(Calendar.MONTH)+1).append("月")
                .append(cal.get(Calendar.DAY_OF_MONTH)).append("日")
                .append(cal.get(Calendar.HOUR_OF_DAY)).append("时")
                .append(cal.get(Calendar.MINUTE)).append("分");
        return buffer.toString();
    }

    public static byte getDistinct() {
        return (byte) (getCurrentWeek() % 2 == 0 ? 2 : 1);
    }

    public static byte getContraryDistinct() {
        return (byte) (getCurrentWeek() % 2 == 0 ? 1 : 2);
    }

    public static void main(String[] args) {
        Integer day = getCurrentDay();
        System.out.println(day);
    }
}
