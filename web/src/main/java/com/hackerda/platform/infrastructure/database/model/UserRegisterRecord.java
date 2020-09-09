package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_register_record
 * @author 
 */
@Data
public class UserRegisterRecord implements Serializable {
    private Long id;

    private String appid;

    private String openid;

    private String phoneNumber;

    private String userName;

    private Integer lifeCycleStatus;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}