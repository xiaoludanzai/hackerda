package com.hackerda.platform.infrastructure.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SubscribeMessage<T> {

    @JSONField(name = "touser")
    private String toUser;

    @JSONField(name = "template_id")
    private String templateId;

    private String page;

    private T data;

}
