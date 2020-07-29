package com.hackerda.spider.support.search.emptyroom;

import com.hackerda.spider.support.search.SearchPost;
import lombok.Data;

/**
 * @author Syaeldon
 * 空教室教务网接口的post数据格式
 */
@Data
public class SearchEmptyRoomPost extends SearchPost {
    /**
     * weeks显示当前周数
     */
    private String weeks;

    private String executiveEducationPlanNumber;
    /**
     * codeCampusListNumber为校区编号
     * 01 主校区
     */
    private String codeCampusListNumber;
    /**
     * teaNum为教学楼编号
     * 01  主楼(东楼)
     * 02  科大
     * 03  主楼(西楼)
     * 04  操场
     * 05  实验楼
     * 06  图书馆
     * 110  管理学院
     * 111  松花四季
     */
    private String teaNum;
    /**
     * wSection为星期的天数和节次
     * 星期天数/节次
     */
    private String wSection;


    public SearchEmptyRoomPost(String weeks, String teaNum, String wSection, String pageNum, String pageSize) {
        this.weeks = weeks;
        this.teaNum = teaNum;
        this.wSection = wSection;
        super.setPageNum(pageNum);
        super.setPageSize(pageSize);
    }

    public static String getBuildingCode(String building) {
        switch (building) {
            case "主楼东":
                return "01";
            case "科厦":
                return "02";
            case "主楼西":
                return "03";
            default:
                throw new IllegalArgumentException("never happen");
        }
    }
}
