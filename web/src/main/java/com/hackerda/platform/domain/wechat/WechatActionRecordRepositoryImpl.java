package com.hackerda.platform.domain.wechat;

import com.hackerda.platform.domain.student.Action;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.infrastructure.database.mapper.WechatActionRecordMapper;
import com.hackerda.platform.infrastructure.database.model.WechatActionRecord;
import com.hackerda.platform.infrastructure.database.model.WechatActionRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WechatActionRecordRepositoryImpl implements WechatActionRecordRepository{

    @Autowired
    private WechatActionRecordMapper wechatActionRecordMapper;

    @Override
    public void save(ActionRecord actionRecord) {

        WechatActionRecord record = new WechatActionRecord();

        record.setAccount(actionRecord.getStudentAccount().getAccount());
        record.setAction(actionRecord.getAction().getCode());
        record.setAppid(actionRecord.getWechatUser().getAppId());
        record.setOpenid(actionRecord.getWechatUser().getOpenId());

        wechatActionRecordMapper.insertSelective(record);


    }

    @Override
    public List<ActionRecord> find(WechatUser wechatUser) {

        WechatActionRecordExample example = new WechatActionRecordExample();
        example.createCriteria()
                .andAppidEqualTo(wechatUser.getAppId())
                .andOpenidEqualTo(wechatUser.getOpenId());

        return wechatActionRecordMapper.selectByExample(example).stream()
                .map(x-> new ActionRecord(new WechatUser(x.getAppid(), x.getOpenid()), Action.getByCode(x.getAction()),
                new StudentAccount(x.getAccount()), x.getGmtCreate(), x.getGmtModify()))
                .collect(Collectors.toList());
    }
}
