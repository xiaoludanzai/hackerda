package com.hackerda.platform.infrastructure.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNull() {
            addCriterion("post_id is null");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNotNull() {
            addCriterion("post_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostIdEqualTo(Long value) {
            addCriterion("post_id =", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotEqualTo(Long value) {
            addCriterion("post_id <>", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThan(Long value) {
            addCriterion("post_id >", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThanOrEqualTo(Long value) {
            addCriterion("post_id >=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThan(Long value) {
            addCriterion("post_id <", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThanOrEqualTo(Long value) {
            addCriterion("post_id <=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdIn(List<Long> values) {
            addCriterion("post_id in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotIn(List<Long> values) {
            addCriterion("post_id not in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdBetween(Long value1, Long value2) {
            addCriterion("post_id between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotBetween(Long value1, Long value2) {
            addCriterion("post_id not between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andPostUserNameIsNull() {
            addCriterion("post_user_name is null");
            return (Criteria) this;
        }

        public Criteria andPostUserNameIsNotNull() {
            addCriterion("post_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andPostUserNameEqualTo(String value) {
            addCriterion("post_user_name =", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameNotEqualTo(String value) {
            addCriterion("post_user_name <>", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameGreaterThan(String value) {
            addCriterion("post_user_name >", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("post_user_name >=", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameLessThan(String value) {
            addCriterion("post_user_name <", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameLessThanOrEqualTo(String value) {
            addCriterion("post_user_name <=", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameLike(String value) {
            addCriterion("post_user_name like", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameNotLike(String value) {
            addCriterion("post_user_name not like", value, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameIn(List<String> values) {
            addCriterion("post_user_name in", values, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameNotIn(List<String> values) {
            addCriterion("post_user_name not in", values, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameBetween(String value1, String value2) {
            addCriterion("post_user_name between", value1, value2, "postUserName");
            return (Criteria) this;
        }

        public Criteria andPostUserNameNotBetween(String value1, String value2) {
            addCriterion("post_user_name not between", value1, value2, "postUserName");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andLikeCountIsNull() {
            addCriterion("like_count is null");
            return (Criteria) this;
        }

        public Criteria andLikeCountIsNotNull() {
            addCriterion("like_count is not null");
            return (Criteria) this;
        }

        public Criteria andLikeCountEqualTo(Integer value) {
            addCriterion("like_count =", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotEqualTo(Integer value) {
            addCriterion("like_count <>", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountGreaterThan(Integer value) {
            addCriterion("like_count >", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("like_count >=", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountLessThan(Integer value) {
            addCriterion("like_count <", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountLessThanOrEqualTo(Integer value) {
            addCriterion("like_count <=", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountIn(List<Integer> values) {
            addCriterion("like_count in", values, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotIn(List<Integer> values) {
            addCriterion("like_count not in", values, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountBetween(Integer value1, Integer value2) {
            addCriterion("like_count between", value1, value2, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("like_count not between", value1, value2, "likeCount");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdIsNull() {
            addCriterion("reply_comment_id is null");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdIsNotNull() {
            addCriterion("reply_comment_id is not null");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdEqualTo(Long value) {
            addCriterion("reply_comment_id =", value, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdNotEqualTo(Long value) {
            addCriterion("reply_comment_id <>", value, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdGreaterThan(Long value) {
            addCriterion("reply_comment_id >", value, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("reply_comment_id >=", value, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdLessThan(Long value) {
            addCriterion("reply_comment_id <", value, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdLessThanOrEqualTo(Long value) {
            addCriterion("reply_comment_id <=", value, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdIn(List<Long> values) {
            addCriterion("reply_comment_id in", values, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdNotIn(List<Long> values) {
            addCriterion("reply_comment_id not in", values, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdBetween(Long value1, Long value2) {
            addCriterion("reply_comment_id between", value1, value2, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyCommentIdNotBetween(Long value1, Long value2) {
            addCriterion("reply_comment_id not between", value1, value2, "replyCommentId");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameIsNull() {
            addCriterion("reply_user_name is null");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameIsNotNull() {
            addCriterion("reply_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameEqualTo(String value) {
            addCriterion("reply_user_name =", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameNotEqualTo(String value) {
            addCriterion("reply_user_name <>", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameGreaterThan(String value) {
            addCriterion("reply_user_name >", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("reply_user_name >=", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameLessThan(String value) {
            addCriterion("reply_user_name <", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameLessThanOrEqualTo(String value) {
            addCriterion("reply_user_name <=", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameLike(String value) {
            addCriterion("reply_user_name like", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameNotLike(String value) {
            addCriterion("reply_user_name not like", value, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameIn(List<String> values) {
            addCriterion("reply_user_name in", values, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameNotIn(List<String> values) {
            addCriterion("reply_user_name not in", values, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameBetween(String value1, String value2) {
            addCriterion("reply_user_name between", value1, value2, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andReplyUserNameNotBetween(String value1, String value2) {
            addCriterion("reply_user_name not between", value1, value2, "replyUserName");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdIsNull() {
            addCriterion("root_comment_id is null");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdIsNotNull() {
            addCriterion("root_comment_id is not null");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdEqualTo(Long value) {
            addCriterion("root_comment_id =", value, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdNotEqualTo(Long value) {
            addCriterion("root_comment_id <>", value, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdGreaterThan(Long value) {
            addCriterion("root_comment_id >", value, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("root_comment_id >=", value, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdLessThan(Long value) {
            addCriterion("root_comment_id <", value, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdLessThanOrEqualTo(Long value) {
            addCriterion("root_comment_id <=", value, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdIn(List<Long> values) {
            addCriterion("root_comment_id in", values, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdNotIn(List<Long> values) {
            addCriterion("root_comment_id not in", values, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdBetween(Long value1, Long value2) {
            addCriterion("root_comment_id between", value1, value2, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andRootCommentIdNotBetween(Long value1, Long value2) {
            addCriterion("root_comment_id not between", value1, value2, "rootCommentId");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeIsNull() {
            addCriterion("identity_code is null");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeIsNotNull() {
            addCriterion("identity_code is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeEqualTo(Integer value) {
            addCriterion("identity_code =", value, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeNotEqualTo(Integer value) {
            addCriterion("identity_code <>", value, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeGreaterThan(Integer value) {
            addCriterion("identity_code >", value, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("identity_code >=", value, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeLessThan(Integer value) {
            addCriterion("identity_code <", value, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeLessThanOrEqualTo(Integer value) {
            addCriterion("identity_code <=", value, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeIn(List<Integer> values) {
            addCriterion("identity_code in", values, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeNotIn(List<Integer> values) {
            addCriterion("identity_code not in", values, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeBetween(Integer value1, Integer value2) {
            addCriterion("identity_code between", value1, value2, "identityCode");
            return (Criteria) this;
        }

        public Criteria andIdentityCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("identity_code not between", value1, value2, "identityCode");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNull() {
            addCriterion("record_status is null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNotNull() {
            addCriterion("record_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusEqualTo(Integer value) {
            addCriterion("record_status =", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotEqualTo(Integer value) {
            addCriterion("record_status <>", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThan(Integer value) {
            addCriterion("record_status >", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_status >=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThan(Integer value) {
            addCriterion("record_status <", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThanOrEqualTo(Integer value) {
            addCriterion("record_status <=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIn(List<Integer> values) {
            addCriterion("record_status in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotIn(List<Integer> values) {
            addCriterion("record_status not in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusBetween(Integer value1, Integer value2) {
            addCriterion("record_status between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("record_status not between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andPostTimeIsNull() {
            addCriterion("post_time is null");
            return (Criteria) this;
        }

        public Criteria andPostTimeIsNotNull() {
            addCriterion("post_time is not null");
            return (Criteria) this;
        }

        public Criteria andPostTimeEqualTo(Date value) {
            addCriterion("post_time =", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotEqualTo(Date value) {
            addCriterion("post_time <>", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeGreaterThan(Date value) {
            addCriterion("post_time >", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("post_time >=", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeLessThan(Date value) {
            addCriterion("post_time <", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeLessThanOrEqualTo(Date value) {
            addCriterion("post_time <=", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeIn(List<Date> values) {
            addCriterion("post_time in", values, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotIn(List<Date> values) {
            addCriterion("post_time not in", values, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeBetween(Date value1, Date value2) {
            addCriterion("post_time between", value1, value2, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotBetween(Date value1, Date value2) {
            addCriterion("post_time not between", value1, value2, "postTime");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIsNull() {
            addCriterion("gmt_modify is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIsNotNull() {
            addCriterion("gmt_modify is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifyEqualTo(Date value) {
            addCriterion("gmt_modify =", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotEqualTo(Date value) {
            addCriterion("gmt_modify <>", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyGreaterThan(Date value) {
            addCriterion("gmt_modify >", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modify >=", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyLessThan(Date value) {
            addCriterion("gmt_modify <", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modify <=", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIn(List<Date> values) {
            addCriterion("gmt_modify in", values, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotIn(List<Date> values) {
            addCriterion("gmt_modify not in", values, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyBetween(Date value1, Date value2) {
            addCriterion("gmt_modify between", value1, value2, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modify not between", value1, value2, "gmtModify");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}