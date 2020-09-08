package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.WechatPlatform;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@ToString(callSuper = true)
public class WechatStudentUserBO extends StudentUserBO{

    private List<StudentWechatBindDetail> wechatOpenidList = new ArrayList<>(0);

    public boolean hasBindApp() {
        return getAppOpenid() != null;
    }


    public StudentWechatBindDetail getAppOpenid(){
        return getOpenId(WechatPlatform.HKXJ_APP);
    }

    public boolean hasBindPlus() {
        return getPlusOpenid() != null;
    }

    public StudentWechatBindDetail getPlusOpenid(){
        return getOpenId(WechatPlatform.HKXJ_PLUS);
    }


    public void bindWechatPlatform(String openid, String appId, WechatPlatform wechatPlatform){
        StudentWechatBindDetail openId = getOpenId(wechatPlatform);
        if(openId != null) {
            openId.bindOpenId(openid);
        }else {
            wechatOpenidList.add(new StudentWechatBindDetail(this.getAccount(), openid, true, appId, wechatPlatform, true));
        }
    }

    public void unbindWechatPlatform(WechatPlatform wechatPlatform){
        StudentWechatBindDetail openId = getOpenId(wechatPlatform);
        if(openId != null) {
            openId.unbind();
        } else {
            log.warn("account {} can't find wechatPlatform {} ", this.getAccount(), wechatPlatform);
        }
    }

    private StudentWechatBindDetail getOpenId(WechatPlatform wechatPlatform){
        for (StudentWechatBindDetail studentWechatBindDetail : wechatOpenidList) {
            if(studentWechatBindDetail.getWechatPlatform() == wechatPlatform){
                return studentWechatBindDetail;
            }
        }
        return null;

    }
}
