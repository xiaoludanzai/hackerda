package com.hackerda.platform.domain.community;

import java.util.List;

public interface CommentRepository {

    void save(CommentBO commentBO);

    List<CommentDetailBO> findDetailByPostId(long postId);

    void update(CommentBO commentBO);

    List<CommentDetailBO> find(RecordStatus recordStatus, long postId);

    long count(RecordStatus recordStatus);
}
