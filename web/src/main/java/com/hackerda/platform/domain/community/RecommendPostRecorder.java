package com.hackerda.platform.domain.community;

import java.util.Date;
import java.util.List;

public interface RecommendPostRecorder {

    List<Long> getPostIdList(Date date);

    void add(long postId, Date date);

    void add(List<Long> postIdList, Date date);

    void remove(long postId, Date date);

    void clear(Date date);
}
