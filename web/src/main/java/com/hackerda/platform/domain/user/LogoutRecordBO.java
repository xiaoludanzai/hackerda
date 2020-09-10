package com.hackerda.platform.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogoutRecordBO {

    private final Long logoutRecordId;

    private final LogoutType logoutType;

    private final String logoutReason;

    private final String operator;

}
