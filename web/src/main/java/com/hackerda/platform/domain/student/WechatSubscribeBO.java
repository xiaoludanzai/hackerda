package com.hackerda.platform.domain.student;

import lombok.Data;

@Data
public class WechatSubscribeBO {

    private Boolean isSubscribe;

    private Integer scene;

    public WechatSubscribeBO(Boolean isSubscribe, Integer scene){
        this.isSubscribe = isSubscribe;
        this.scene = scene;
    }

    public boolean isNotNull(){
        return isSubscribe != null && scene != null;
    }


}
