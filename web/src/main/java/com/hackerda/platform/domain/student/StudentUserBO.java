package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.WechatPlatform;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.utils.DESUtil;
import lombok.Data;

import java.util.List;

@Data
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

    private List<WechatOpenidBO> wechatOpenidList;

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
        for (WechatOpenidBO wechatOpenidBO : wechatOpenidList) {
            if(wechatOpenidBO.getWechatPlatform() == WechatPlatform.HKXJ_APP){
                return wechatOpenidBO;
            }
        }
        return null;
    }

    public boolean hasBindPlus() {
        return getPlusOpenid() != null;
    }

    public WechatOpenidBO getPlusOpenid(){
        for (WechatOpenidBO wechatOpenidBO : wechatOpenidList) {
            if(wechatOpenidBO.getWechatPlatform() == WechatPlatform.HKXJ_PLUS){
                return wechatOpenidBO;
            }
        }
        return null;
    }

}
