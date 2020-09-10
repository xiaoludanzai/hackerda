package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_logout_record
 * @author 
 */
@Data
public class UserLogoutRecord implements Serializable {
    private Long id;

    private Long logoutRecordId;

    private Integer logoutType;

    private String logoutReason;

    private String operator;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}