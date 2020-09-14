package com.hackerda.platform.infrastructure.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageExample() {
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

        public Criteria andSenderUserNameIsNull() {
            addCriterion("sender_user_name is null");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameIsNotNull() {
            addCriterion("sender_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameEqualTo(String value) {
            addCriterion("sender_user_name =", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameNotEqualTo(String value) {
            addCriterion("sender_user_name <>", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameGreaterThan(String value) {
            addCriterion("sender_user_name >", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("sender_user_name >=", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameLessThan(String value) {
            addCriterion("sender_user_name <", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameLessThanOrEqualTo(String value) {
            addCriterion("sender_user_name <=", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameLike(String value) {
            addCriterion("sender_user_name like", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameNotLike(String value) {
            addCriterion("sender_user_name not like", value, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameIn(List<String> values) {
            addCriterion("sender_user_name in", values, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameNotIn(List<String> values) {
            addCriterion("sender_user_name not in", values, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameBetween(String value1, String value2) {
            addCriterion("sender_user_name between", value1, value2, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderUserNameNotBetween(String value1, String value2) {
            addCriterion("sender_user_name not between", value1, value2, "senderUserName");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeIsNull() {
            addCriterion("sender_identity_category_code is null");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeIsNotNull() {
            addCriterion("sender_identity_category_code is not null");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeEqualTo(Integer value) {
            addCriterion("sender_identity_category_code =", value, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeNotEqualTo(Integer value) {
            addCriterion("sender_identity_category_code <>", value, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeGreaterThan(Integer value) {
            addCriterion("sender_identity_category_code >", value, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender_identity_category_code >=", value, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeLessThan(Integer value) {
            addCriterion("sender_identity_category_code <", value, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeLessThanOrEqualTo(Integer value) {
            addCriterion("sender_identity_category_code <=", value, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeIn(List<Integer> values) {
            addCriterion("sender_identity_category_code in", values, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeNotIn(List<Integer> values) {
            addCriterion("sender_identity_category_code not in", values, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeBetween(Integer value1, Integer value2) {
            addCriterion("sender_identity_category_code between", value1, value2, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andSenderIdentityCategoryCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("sender_identity_category_code not between", value1, value2, "senderIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameIsNull() {
            addCriterion("receiver_user_name is null");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameIsNotNull() {
            addCriterion("receiver_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameEqualTo(String value) {
            addCriterion("receiver_user_name =", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameNotEqualTo(String value) {
            addCriterion("receiver_user_name <>", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameGreaterThan(String value) {
            addCriterion("receiver_user_name >", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("receiver_user_name >=", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameLessThan(String value) {
            addCriterion("receiver_user_name <", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameLessThanOrEqualTo(String value) {
            addCriterion("receiver_user_name <=", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameLike(String value) {
            addCriterion("receiver_user_name like", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameNotLike(String value) {
            addCriterion("receiver_user_name not like", value, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameIn(List<String> values) {
            addCriterion("receiver_user_name in", values, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameNotIn(List<String> values) {
            addCriterion("receiver_user_name not in", values, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameBetween(String value1, String value2) {
            addCriterion("receiver_user_name between", value1, value2, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverUserNameNotBetween(String value1, String value2) {
            addCriterion("receiver_user_name not between", value1, value2, "receiverUserName");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeIsNull() {
            addCriterion("receiver_identity_category_code is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeIsNotNull() {
            addCriterion("receiver_identity_category_code is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeEqualTo(Integer value) {
            addCriterion("receiver_identity_category_code =", value, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeNotEqualTo(Integer value) {
            addCriterion("receiver_identity_category_code <>", value, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeGreaterThan(Integer value) {
            addCriterion("receiver_identity_category_code >", value, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("receiver_identity_category_code >=", value, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeLessThan(Integer value) {
            addCriterion("receiver_identity_category_code <", value, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeLessThanOrEqualTo(Integer value) {
            addCriterion("receiver_identity_category_code <=", value, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeIn(List<Integer> values) {
            addCriterion("receiver_identity_category_code in", values, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeNotIn(List<Integer> values) {
            addCriterion("receiver_identity_category_code not in", values, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeBetween(Integer value1, Integer value2) {
            addCriterion("receiver_identity_category_code between", value1, value2, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andReceiverIdentityCategoryCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("receiver_identity_category_code not between", value1, value2, "receiverIdentityCategoryCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeIsNull() {
            addCriterion("message_trigger_source_code is null");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeIsNotNull() {
            addCriterion("message_trigger_source_code is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeEqualTo(Integer value) {
            addCriterion("message_trigger_source_code =", value, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeNotEqualTo(Integer value) {
            addCriterion("message_trigger_source_code <>", value, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeGreaterThan(Integer value) {
            addCriterion("message_trigger_source_code >", value, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("message_trigger_source_code >=", value, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeLessThan(Integer value) {
            addCriterion("message_trigger_source_code <", value, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeLessThanOrEqualTo(Integer value) {
            addCriterion("message_trigger_source_code <=", value, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeIn(List<Integer> values) {
            addCriterion("message_trigger_source_code in", values, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeNotIn(List<Integer> values) {
            addCriterion("message_trigger_source_code not in", values, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeBetween(Integer value1, Integer value2) {
            addCriterion("message_trigger_source_code between", value1, value2, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageTriggerSourceCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("message_trigger_source_code not between", value1, value2, "messageTriggerSourceCode");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdIsNull() {
            addCriterion("message_source_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdIsNotNull() {
            addCriterion("message_source_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdEqualTo(Long value) {
            addCriterion("message_source_id =", value, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdNotEqualTo(Long value) {
            addCriterion("message_source_id <>", value, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdGreaterThan(Long value) {
            addCriterion("message_source_id >", value, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("message_source_id >=", value, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdLessThan(Long value) {
            addCriterion("message_source_id <", value, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdLessThanOrEqualTo(Long value) {
            addCriterion("message_source_id <=", value, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdIn(List<Long> values) {
            addCriterion("message_source_id in", values, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdNotIn(List<Long> values) {
            addCriterion("message_source_id not in", values, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdBetween(Long value1, Long value2) {
            addCriterion("message_source_id between", value1, value2, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageSourceIdNotBetween(Long value1, Long value2) {
            addCriterion("message_source_id not between", value1, value2, "messageSourceId");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeIsNull() {
            addCriterion("message_type_code is null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeIsNotNull() {
            addCriterion("message_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeEqualTo(Integer value) {
            addCriterion("message_type_code =", value, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeNotEqualTo(Integer value) {
            addCriterion("message_type_code <>", value, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeGreaterThan(Integer value) {
            addCriterion("message_type_code >", value, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("message_type_code >=", value, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeLessThan(Integer value) {
            addCriterion("message_type_code <", value, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeLessThanOrEqualTo(Integer value) {
            addCriterion("message_type_code <=", value, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeIn(List<Integer> values) {
            addCriterion("message_type_code in", values, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeNotIn(List<Integer> values) {
            addCriterion("message_type_code not in", values, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeBetween(Integer value1, Integer value2) {
            addCriterion("message_type_code between", value1, value2, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andMessageTypeCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("message_type_code not between", value1, value2, "messageTypeCode");
            return (Criteria) this;
        }

        public Criteria andHasReadIsNull() {
            addCriterion("has_read is null");
            return (Criteria) this;
        }

        public Criteria andHasReadIsNotNull() {
            addCriterion("has_read is not null");
            return (Criteria) this;
        }

        public Criteria andHasReadEqualTo(Byte value) {
            addCriterion("has_read =", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadNotEqualTo(Byte value) {
            addCriterion("has_read <>", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadGreaterThan(Byte value) {
            addCriterion("has_read >", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadGreaterThanOrEqualTo(Byte value) {
            addCriterion("has_read >=", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadLessThan(Byte value) {
            addCriterion("has_read <", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadLessThanOrEqualTo(Byte value) {
            addCriterion("has_read <=", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadIn(List<Byte> values) {
            addCriterion("has_read in", values, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadNotIn(List<Byte> values) {
            addCriterion("has_read not in", values, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadBetween(Byte value1, Byte value2) {
            addCriterion("has_read between", value1, value2, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadNotBetween(Byte value1, Byte value2) {
            addCriterion("has_read not between", value1, value2, "hasRead");
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