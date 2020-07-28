package com.hackerda.platform.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMsg {

    private int errorCode;
    private String msg;
}
