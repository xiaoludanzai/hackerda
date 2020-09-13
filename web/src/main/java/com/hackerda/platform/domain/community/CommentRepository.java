package com.hackerda.platform.domain.community;

import java.util.List;

public interface CommentRepository {

    void save(CommentBO commentBO);

    List<CommentDetailBO> findDetailByPostId(long postId);

    void update(CommentBO commentBO);

    List<CommentDetailBO> findByPost(RecordStatus recordStatus, long postId);

    List<CommentDetailBO> findByPostUserName(RecordStatus recordStatus, String userName);

    List<CommentDetailBO> findByReplyUserName(RecordStatus recordStatus, String userName);

    long count(RecordStatus recordStatus);
}
