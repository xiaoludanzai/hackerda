package com.hackerda.platform.domain.community;

import java.util.Collection;

public interface LikeCountService {

    void increment(LikeType likeType, long likeContentId, String userName);

    void decrement(LikeType likeType, long likeContentId, String userName);

    boolean hasLike(LikeType likeType, long likeContentId, String userName);

    long likeCount(LikeType likeType, long likeContentId);

    void setCount(LikeType likeType, long likeContentId, Collection<String> userNameCollection);
}
