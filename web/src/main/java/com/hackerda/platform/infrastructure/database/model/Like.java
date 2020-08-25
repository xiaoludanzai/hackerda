package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * like
 * @author 
 */
@Data
public class Like implements Serializable {
    private Long id;

    private Integer likeType;

    private Long typeContentId;

    private String userName;

    private Date likeTime;

    private Byte show;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}