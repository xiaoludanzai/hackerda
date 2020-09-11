package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.wechat.WechatUser;
import org.assertj.core.util.Lists;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class WechatStudentUserBOTest {


    @Test
    public void bindAndRevoke() {

        WechatStudentUserBO bo = new WechatStudentUserBO();

        bo.setBindWechatUser(Lists.newArrayList(new WechatUser("appid1", "1"), new WechatUser("appid2", "1")));

        // 绑定新记录解绑旧记录
        bo.bindWechatUser(new WechatUser("appid3", "1"));

        bo.revokeWechatUser("appid1");

        assertThat(bo.getNewBindWechatUser()).isEqualTo(Lists.newArrayList(new WechatUser("appid3", "1")));
        assertThat(bo.getRevokeWechatUser()).isEqualTo(Lists.newArrayList(new WechatUser("appid1", "1")));


        // 解绑新绑定的记录
        bo.revokeWechatUser("appid3");
        assertThat(bo.getRevokeWechatUser()).isEqualTo(Lists.newArrayList(new WechatUser("appid1", "1")));
        assertThat(bo.getNewBindWechatUser()).isEmpty();

        // 同一个appId 绑定不同的账号
        bo.bindWechatUser(new WechatUser("appid1", "2"));
        assertThat(bo.getNewBindWechatUser()).isEqualTo(Lists.newArrayList(new WechatUser("appid1", "2")));;

    }

}