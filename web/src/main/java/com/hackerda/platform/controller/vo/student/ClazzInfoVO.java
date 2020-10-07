package com.hackerda.platform.controller.vo.student;

import lombok.Data;

import java.util.List;

@Data
public class ClazzInfoVO<T> {

    private int size;

    private String grade;

    private List<T> infoList;
}
