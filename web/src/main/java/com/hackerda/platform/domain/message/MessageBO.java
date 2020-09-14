package com.hackerda.platform.domain.message;

import com.hackerda.platform.domain.community.IdentityCategory;
import lombok.Data;

@Data
public class MessageBO {

    private Long id;

    private String senderUserName;

    private IdentityCategory senderIdentityCategory;

    private String receiverUserName;

    private IdentityCategory receiverIdentityCategory;

    private MessageTriggerSource messageTriggerSource;

    private MessageType messageType;

    private Long messageSourceId;

    private boolean hasRead;
}
