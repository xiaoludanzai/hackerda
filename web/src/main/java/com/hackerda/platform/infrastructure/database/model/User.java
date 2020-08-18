package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String userName;

    private String nickName;

    private String password;

    private String salt;

    private String avatarPath;

    private String email;

    private String mobile;

    private Integer gender;

    private String introduction;

    private Integer userType;

    private Byte useDefaultPassword;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}