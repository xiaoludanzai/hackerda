package com.hackerda.platform.domain.community;

import java.util.Collection;
import java.util.List;

public interface LikeRepository {

    void save(LikeBO likeBO);

    void update(LikeBO likeBO);

    LikeBO find(String userName, LikeType likeType, long typeId);

    List<LikeBO> findByReplyUserName(String userName);

    List<LikeBO> findByIdList(List<Long> idList);

    List<LikeBO> findShow(LikeType likeType, long typeId);

    long countByReceiver(String userName, List<IdentityCategory> identityCategoryList);
}
