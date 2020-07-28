package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.utils.DESUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Slf4j
public class StudentUserBO {

    private Integer account;

    private String password;

    private String name;

    private String sex;

    private String ethnic;

    private Integer urpClassNum;

    private Boolean isCorrect;

    private String academyName;

    private String subjectName;

    private String className;

    private List<WechatOpenidBO> wechatOpenidList = new ArrayList<>(0);

    private String key;

    private boolean saveOrUpdate;

    /**
     * 获取年级
     * @return 2017级返回2017
     */
    public String getGrade(){
        return account.toString().substring(0, 4);
    }


    public String getEnablePassword(String key) {
        return DESUtil.decrypt(this.password, key);
    }

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


    public void updatePassword(String enablePassword) {
        this.password = DESUtil.encrypt(enablePassword, account + key);
        this.saveOrUpdate = true;
    }

    public boolean checkEnablePasswordIsCorrect(String enablePassword) {
        return DESUtil.encrypt(enablePassword, account + key).equals(password);
    }

    public void bindWechatPlatform(String openid, String appId, WechatPlatform wechatPlatform){
        WechatOpenidBO openId = getOpenId(wechatPlatform);
        if(openId != null) {
            openId.bindOpenId(openid);
        }else {
            wechatOpenidList.add(new WechatOpenidBO(this.account, openid, true, appId, wechatPlatform, true));
        }
    }

    public void unbindWechatPlatform(WechatPlatform wechatPlatform){
        WechatOpenidBO openId = getOpenId(wechatPlatform);
        if(openId != null) {
            openId.unbind();
        } else {
            log.warn("account {} can't find wechatPlatform {} ", this.account, wechatPlatform);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StudentUserBO that = (StudentUserBO) o;

        return new EqualsBuilder()
                .append(account, that.account)
                .append(password, that.password)
                .append(name, that.name)
                .append(sex, that.sex)
                .append(ethnic, that.ethnic)
                .append(urpClassNum, that.urpClassNum)
                .append(academyName, that.academyName)
                .append(subjectName, that.subjectName)
                .append(className, that.className)
                .append(wechatOpenidList, that.wechatOpenidList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(account)
                .append(password)
                .append(name)
                .append(sex)
                .append(ethnic)
                .append(urpClassNum)
                .append(academyName)
                .append(subjectName)
                .append(className)
                .append(wechatOpenidList)
                .toHashCode();
    }
}
