package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.WechatOpenidMapper;
import com.hackerda.platform.pojo.ScheduleTask;
import com.hackerda.platform.pojo.WechatOpenid;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface WechatOpenIdExtMapper extends WechatOpenidMapper {

    void insertBatch(List<WechatOpenid> wechatOpenidList);

    List<WechatOpenid> selectBySubscribe(ScheduleTask task);
}
