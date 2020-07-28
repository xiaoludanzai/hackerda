package com.hackerda.platform.infrastructure.dao;

import com.hackerda.platform.mapper.WechatBindRecordMapper;
import com.hackerda.platform.pojo.WechatBindRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WechatBindRecordDao {
    @Resource
    private WechatBindRecordMapper wechatBindRecordMapper;


    public void insertSelective(WechatBindRecord wechatBindRecord){
        wechatBindRecordMapper.insertSelective(wechatBindRecord);
    }

}
