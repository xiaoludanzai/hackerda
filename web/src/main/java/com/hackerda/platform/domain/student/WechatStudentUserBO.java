package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.utils.DESUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class WechatStudentUserBO extends StudentUserBO{

    private List<WechatOpenidBO> wechatOpenidList = new ArrayList<>(0);

    public boolean hasBindApp() {
        return getAppOpenid() != null;
    }


    public WechatOpenidBO getAppOpenid(){
        return getOpenId(WechatPlatform.HKXJ_APP);
    }

    public boolean hasBindPlus() {
        return getPlusOpenid() != null;
    }

    public WechatOpenidBO getPlusOpenid(){
        return getOpenId(WechatPlatform.HKXJ_PLUS);
    }


    public void bindWechatPlatform(String openid, String appId, WechatPlatform wechatPlatform){
        WechatOpenidBO openId = getOpenId(wechatPlatform);
        if(openId != null) {
            openId.bindOpenId(openid);
        }else {
            wechatOpenidList.add(new WechatOpenidBO(this.getAccount(), openid, true, appId, wechatPlatform, true));
        }
    }

    public void unbindWechatPlatform(WechatPlatform wechatPlatform){
        WechatOpenidBO openId = getOpenId(wechatPlatform);
        if(openId != null) {
            openId.unbind();
        } else {
            log.warn("account {} can't find wechatPlatform {} ", this.getAccount(), wechatPlatform);
        }
    }

    private WechatOpenidBO getOpenId(WechatPlatform wechatPlatform){
        for (WechatOpenidBO wechatOpenidBO : wechatOpenidList) {
            if(wechatOpenidBO.getWechatPlatform() == wechatPlatform){
                return wechatOpenidBO;
            }
        }
        return null;

    }


}
