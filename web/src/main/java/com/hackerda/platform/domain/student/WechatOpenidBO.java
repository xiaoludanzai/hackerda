package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.WechatPlatform;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Getter
@ToString
public class WechatOpenidBO {
    private final int account;

    private String openid;

    private boolean isBind;

    private final String appId;

    private final WechatPlatform wechatPlatform;

    private boolean saveOrUpdate;


    @Setter
    private List<WechatSubscribeBO> wechatSubscribeBOList;

    public WechatOpenidBO(int account, String openid, Boolean isBind, String appId, WechatPlatform wechatPlatform,
                          boolean saveOrUpdate){
        this.account = account;
        this.openid = openid;
        this.appId = appId;
        this.isBind = BooleanUtils.toBoolean(isBind);
        this.wechatPlatform = wechatPlatform;
        this.saveOrUpdate = saveOrUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WechatOpenidBO that = (WechatOpenidBO) o;

        return new EqualsBuilder()
                .append(openid, that.openid)
                .append(appId, that.appId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(openid)
                .append(appId)
                .toHashCode();
    }

    public void bindOpenId(String openid){
        this.openid = openid;
        this.isBind = true;
        this.saveOrUpdate = true;
    }
}
