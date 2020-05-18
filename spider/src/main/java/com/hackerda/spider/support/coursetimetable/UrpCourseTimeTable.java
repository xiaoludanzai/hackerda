package com.hackerda.spider.support.coursetimetable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yuki
 * @date 2019/8/29 23:05
 */
@Data
public class UrpCourseTimeTable {
    /**
     * 所有学分
     */
    private Double allUnits;

    /**
     * 所有的课程时间表，包括信息和详情
     */
    @JSONField(name = "xkxx")
    private List<HashMap<String, UrpCourseTimeTableItem>> details;

    public boolean hasSchoolCourse() {
        for (HashMap<String, UrpCourseTimeTableItem> table : this.getDetails()) {
            for (Map.Entry<String, UrpCourseTimeTableItem> entry : table.entrySet()) {
                if (entry.getValue().getTimeAndPlaceList().size() != 0) {
                    return true;
                }
            }
        }
        return false;

    }



}
