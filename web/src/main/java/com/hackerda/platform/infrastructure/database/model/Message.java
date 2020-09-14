package com.hackerda.platform.infrastructure.database.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * message
 * @author 
 */
@Data
public class Message implements Serializable {
    private Long id;

    private String senderUserName;

    private Integer senderIdentityCategoryCode;

    private String receiverUserName;

    private Integer receiverIdentityCategoryCode;

    private Integer messageTriggerSourceCode;

    private Long messageSourceId;

    private Integer messageTypeCode;

    private Byte hasRead;

    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;
}