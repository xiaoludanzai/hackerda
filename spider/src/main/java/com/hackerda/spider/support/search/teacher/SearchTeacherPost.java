package com.hackerda.spider.support.search.teacher;


import com.hackerda.spider.support.search.SearchPost;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SearchTeacherPost extends SearchPost {
    /**
     */
    private String departmentNum = "";
    /**
     * 教师名称
     */
    private String teacherName = "";
}
