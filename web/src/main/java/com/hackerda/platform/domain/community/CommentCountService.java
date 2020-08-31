package com.hackerda.platform.domain.community;

public interface CommentCountService {

    long increment(long postId);

    long decrement(long postId);

    long count(long postId);

    void setCount(long postId, long size);
}
