package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.WechatBindRecordMapper;
import com.hackerda.platform.infrastructure.database.model.WechatBindRecord;
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
