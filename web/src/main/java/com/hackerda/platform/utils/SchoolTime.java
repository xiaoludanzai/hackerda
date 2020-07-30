package com.hackerda.platform.utils;

import com.hackerda.platform.utils.Term;
import lombok.Data;

/**
 * @author Yuki
 * @date 2019/9/1 14:48
 */
@Data
public class SchoolTime {
    /**
     * 星期几
     */
    private int day;
    /**
     * 学号的教学周
     */
    private int schoolWeek;

    private Term term;

    public String getDayOfWeekChinese(){
        return getDayOfWeekChinese(day);
    }

    private static String getDayOfWeekChinese(int day) {
        switch (day){
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                throw new IllegalArgumentException("never happen");
        }
    }
}
