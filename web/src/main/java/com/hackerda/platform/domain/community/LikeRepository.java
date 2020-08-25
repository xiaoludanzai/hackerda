package com.hackerda.platform.domain.community;

import java.util.List;

public interface LikeRepository {

    void save(LikeBO likeBO);

    void update(LikeBO likeBO);

    LikeBO find(String userName, LikeType likeType, long typeId);

    List<LikeBO> findAll(LikeType likeType, long typeId);

    List<LikeBO> findShow(LikeType likeType, long typeId);
}
