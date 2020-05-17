package com.hackerda.platform.spider.newmodel.coursetimetable;

import com.hackerda.platform.pojo.CourseTimetable;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuki
 * @date 2019/8/29 23:05
 */
@Data
public class UrpCourseTimeTableForSpider {
    /**
     * 所有学分
     */
    private Double allUnits;

    /**
     * 所有的课程时间表，包括信息和详情
     */
    @JSONField(name = "xkxx")
    private List<HashMap<String, UrpCourseTimeTable>> details;


    public List<CourseTimetable> adaptToList(){

        return details.stream()
                .flatMap(x -> x.values().stream().map(UrpCourseTimeTable::adapterToCourseTimetable))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
