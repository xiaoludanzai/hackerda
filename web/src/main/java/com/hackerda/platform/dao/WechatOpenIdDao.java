package com.hackerda.platform.dao;


import com.hackerda.platform.mapper.ext.WechatOpenIdExtMapper;
import com.hackerda.platform.pojo.ScheduleTask;
import com.hackerda.platform.pojo.WechatOpenid;
import com.hackerda.platform.pojo.example.WechatOpenidExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatOpenIdDao {
    @Autowired
    private WechatOpenIdExtMapper wechatOpenIdExtMapper;

    public List<WechatOpenid> selectByPojo(WechatOpenid wechatOpenid) {
        WechatOpenidExample example = new WechatOpenidExample();
        WechatOpenidExample.Criteria criteria = example.createCriteria();

        if (wechatOpenid.getOpenid() != null) {
            criteria.andOpenidEqualTo(wechatOpenid.getOpenid());
        }
        if (wechatOpenid.getAppid() != null) {
            criteria.andAppidEqualTo(wechatOpenid.getAppid());
        }
        if (wechatOpenid.getAccount() != null) {
            criteria.andAccountEqualTo(wechatOpenid.getAccount());
        }

        return wechatOpenIdExtMapper.selectByExample(example);
    }

    public WechatOpenid selectByUniqueKey(String appid, String openid) {
        WechatOpenid wechatOpenid = new WechatOpenid().setOpenid(openid).setAppid(appid);
        return selectByPojo(wechatOpenid).stream().findFirst().orElse(null);
    }

    public WechatOpenid selectBindUser(Integer account) {
        WechatOpenid wechatOpenid = new WechatOpenid().setIsBind(true).setAccount(account);
        List<WechatOpenid> openidList = selectByPojo(wechatOpenid);

        if(openidList.size() > 1) {
            throw new RuntimeException(account + "被多个openId绑定");
        }

        return openidList.stream().findFirst().orElse(null);
    }


    public void insertSelective(WechatOpenid wechatOpenid){
        wechatOpenIdExtMapper.insertSelective(wechatOpenid);
    }

    public void updateByPrimaryKeySelective(WechatOpenid wechatOpenid){
        wechatOpenIdExtMapper.updateByPrimaryKeySelective(wechatOpenid);
    }

    public List<WechatOpenid> getOpenid(String openid) {
        WechatOpenidExample wechatOpenidExample = new WechatOpenidExample();
        wechatOpenidExample.createCriteria().andOpenidEqualTo(openid);
        return wechatOpenIdExtMapper.selectByExample(wechatOpenidExample);
    }


    public void openIdUnbind(String openid, String appid) {
        WechatOpenidExample example = new WechatOpenidExample();
        example.createCriteria()
                .andAppidEqualTo(appid)
                .andOpenidEqualTo(openid);

        wechatOpenIdExtMapper.updateByExampleSelective(new WechatOpenid().setIsBind(false), example);
    }


    public void openIdUnbindAllPlatform(int account) {
        WechatOpenidExample wechatOpenidExample = new WechatOpenidExample();
        wechatOpenidExample.createCriteria().andAccountEqualTo(account);
        wechatOpenIdExtMapper.updateByExampleSelective(new WechatOpenid().setIsBind(false), wechatOpenidExample);
    }


    public List<String> getAllOpenidsFromOneClass(int classId, String openid, String appid) {
        return wechatOpenIdExtMapper.getAllOpenidsFromOneClass(classId, openid);
    }

    public List<WechatOpenid> selectBySubscribe(ScheduleTask scheduleTask){
        return wechatOpenIdExtMapper.selectBySubscribe(scheduleTask);
    }

    public void saveOrUpdate(WechatOpenid wechatOpenid){
        wechatOpenIdExtMapper.saveOrUpdate(wechatOpenid);
    }
}
