package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * image_info
 * @author 
 */
@Data
public class ImageInfoDO implements Serializable {
    private Long id;

    private String url;

    private String fileId;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}