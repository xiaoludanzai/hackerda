package com.hackerda.platform.domain.student;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.exception.BusinessException;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class StudentAccount {

    private final String account;

    public StudentAccount(String account){

        if(account == null || account.length() != 10) {
            throw new BusinessException(ErrorCode.ACCOUNT_OR_PASSWORD_INVALID, "账号长度错误");
        }
        this.account = account;
    }

    public StudentAccount(int account){
        this(String.valueOf(account));
    }

    public String getAccount() {
        return account;
    }

    public int getInt() {
        return Integer.parseInt(account);
    }

    public String getGrade(){
        return account.substring(0, 4);
    }

}
