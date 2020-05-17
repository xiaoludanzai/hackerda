package com.hackerda.platform.mapper;

import com.hackerda.platform.pojo.wechat.SubscribeGradeUpdate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface SubscribeGradeUpdateMapper {
    String isOpenidSubscribed(String openid);

    int insert(SubscribeGradeUpdate subscribeGradeUpdate);

    List<SubscribeGradeUpdate> getSubscribedList();


}
