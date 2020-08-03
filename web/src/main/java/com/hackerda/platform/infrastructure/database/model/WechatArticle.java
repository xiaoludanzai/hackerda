package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * wechat_article
 * @author 
 */
@Data
public class WechatArticle implements Serializable {
    private Integer id;

    private String mediaId;

    private Integer orderSeq;

    private String title;

    private String coverUrl;

    private String contentUrl;

    private Date updateTime;

    private boolean show;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}