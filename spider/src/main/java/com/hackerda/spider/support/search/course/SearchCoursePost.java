package com.hackerda.spider.support.search.course;


import com.hackerda.spider.support.search.SearchPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class SearchCoursePost extends SearchPost {
    /**
     * 开课学院号
     */
    private String academyCode = "";
    private String courseName = "";
    private String courseNumber = "";
    /**
     * 课序号
     */
    private String courseOrderNumber = "";
    private String courseType = "";
}
