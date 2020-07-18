package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.WechatPlatform;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Getter
@Setter
@ToString
public class WechatOpenidBO {

    private String openid;

    private boolean isBind;

    private String appId;

    private WechatPlatform wechatPlatform;

    private List<WechatSubscribeBO> wechatSubscribeBOList;

    public WechatOpenidBO(String openid, Boolean isBind, String appId, WechatPlatform wechatPlatform){
        this.openid = openid;
        this.appId = appId;
        this.isBind = isBind;
        this.wechatPlatform = wechatPlatform;
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
}
