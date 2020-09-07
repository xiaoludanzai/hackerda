package com.hackerda.platform.domain.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PhoneNumber {

    /**
     * 手机号格式校验正则
     */
    public static final String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    /**
     * 手机号脱敏筛选正则
     */
    public static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 手机号脱敏替换正则
     */
    public static final String PHONE_BLUR_REPLACE_REGEX = "$1****$2";


    private final String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        if (StringUtils.isNotEmpty(phoneNumber) && phoneNumber.matches(PHONE_REGEX)) {
            this.phoneNumber = phoneNumber;
        }
        else {
            throw new RuntimeException("手机号格式不正确: "+ phoneNumber);
        }

    }

    public String getBlurNUmber() {
        return phoneNumber.replaceAll(PHONE_BLUR_REGEX, PHONE_BLUR_REPLACE_REGEX);
    }

    public String getEnableNumber() {
        return this.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return new EqualsBuilder()
                .append(phoneNumber, that.phoneNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(phoneNumber)
                .toHashCode();
    }

}
