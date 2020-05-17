package com.hackerda.spider.support;

import com.google.common.base.MoreObjects;
import lombok.Data;

/**
 * @author JR Chan
 * @date 2018/12/15
 */
@Data
public class UrpStudentInfo {
    public String academy;
    private Integer account;
    private String name;
    private String sex;
    private String ethnic;
    private String major;
    private String classname;
}
