package com.hackerda.platform.infrastructure.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserLogoutRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserLogoutRecordExample() {
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

        public Criteria andLogoutRecordIdIsNull() {
            addCriterion("logout_record_id is null");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdIsNotNull() {
            addCriterion("logout_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdEqualTo(Long value) {
            addCriterion("logout_record_id =", value, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdNotEqualTo(Long value) {
            addCriterion("logout_record_id <>", value, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdGreaterThan(Long value) {
            addCriterion("logout_record_id >", value, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdGreaterThanOrEqualTo(Long value) {
            addCriterion("logout_record_id >=", value, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdLessThan(Long value) {
            addCriterion("logout_record_id <", value, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdLessThanOrEqualTo(Long value) {
            addCriterion("logout_record_id <=", value, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdIn(List<Long> values) {
            addCriterion("logout_record_id in", values, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdNotIn(List<Long> values) {
            addCriterion("logout_record_id not in", values, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdBetween(Long value1, Long value2) {
            addCriterion("logout_record_id between", value1, value2, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutRecordIdNotBetween(Long value1, Long value2) {
            addCriterion("logout_record_id not between", value1, value2, "logoutRecordId");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeIsNull() {
            addCriterion("logout_type is null");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeIsNotNull() {
            addCriterion("logout_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeEqualTo(Integer value) {
            addCriterion("logout_type =", value, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeNotEqualTo(Integer value) {
            addCriterion("logout_type <>", value, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeGreaterThan(Integer value) {
            addCriterion("logout_type >", value, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("logout_type >=", value, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeLessThan(Integer value) {
            addCriterion("logout_type <", value, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeLessThanOrEqualTo(Integer value) {
            addCriterion("logout_type <=", value, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeIn(List<Integer> values) {
            addCriterion("logout_type in", values, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeNotIn(List<Integer> values) {
            addCriterion("logout_type not in", values, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeBetween(Integer value1, Integer value2) {
            addCriterion("logout_type between", value1, value2, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("logout_type not between", value1, value2, "logoutType");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonIsNull() {
            addCriterion("logout_reason is null");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonIsNotNull() {
            addCriterion("logout_reason is not null");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonEqualTo(String value) {
            addCriterion("logout_reason =", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonNotEqualTo(String value) {
            addCriterion("logout_reason <>", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonGreaterThan(String value) {
            addCriterion("logout_reason >", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonGreaterThanOrEqualTo(String value) {
            addCriterion("logout_reason >=", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonLessThan(String value) {
            addCriterion("logout_reason <", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonLessThanOrEqualTo(String value) {
            addCriterion("logout_reason <=", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonLike(String value) {
            addCriterion("logout_reason like", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonNotLike(String value) {
            addCriterion("logout_reason not like", value, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonIn(List<String> values) {
            addCriterion("logout_reason in", values, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonNotIn(List<String> values) {
            addCriterion("logout_reason not in", values, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonBetween(String value1, String value2) {
            addCriterion("logout_reason between", value1, value2, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andLogoutReasonNotBetween(String value1, String value2) {
            addCriterion("logout_reason not between", value1, value2, "logoutReason");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("`operator` is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("`operator` is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("`operator` =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("`operator` <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("`operator` >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("`operator` >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("`operator` <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("`operator` <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("`operator` like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("`operator` not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("`operator` in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("`operator` not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("`operator` between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("`operator` not between", value1, value2, "operator");
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