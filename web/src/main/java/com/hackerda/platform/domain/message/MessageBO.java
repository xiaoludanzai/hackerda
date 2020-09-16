package com.hackerda.platform.domain.message;

import com.hackerda.platform.domain.community.IdentityCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder
public class MessageBO {

    @Setter
    private Long id;

    private final String senderUserName;

    private final IdentityCategory senderIdentityCategory;

    private final String receiverUserName;

    private final IdentityCategory receiverIdentityCategory;

    private final MessageTriggerSource messageTriggerSource;

    private final MessageType messageType;

    private final Long messageSourceId;

    @Setter
    private boolean hasRead;

    private final Date createTime;


    /**
     * 是否是自己触发的消息
     * 譬如给回复自己的评论，给自己点赞
     *
     * @return 如果是则返回true
     */
    public boolean isTriggerBySelf() {
       return senderUserName.equals(receiverUserName);
    }

}
