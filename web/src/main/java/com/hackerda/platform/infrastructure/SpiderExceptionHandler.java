package com.hackerda.platform.infrastructure;

import com.hackerda.platform.infrastructure.dao.StudentUserDao;
import com.hackerda.platform.service.OpenIdService;
import com.hackerda.spider.IExceptionHandler;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import org.springframework.stereotype.Component;

@Component
public class SpiderExceptionHandler implements IExceptionHandler {

    private final StudentUserDao studentUserDao;
    private final OpenIdService openIdService;

    public SpiderExceptionHandler(StudentUserDao studentUserDao, OpenIdService openIdService) {
        this.studentUserDao = studentUserDao;
        this.openIdService = openIdService;
    }


    @Override
    public void handle(Exception e, String account) {

        if(e instanceof PasswordUnCorrectException){
            studentUserDao.updatePasswordUnCorrect(Integer.parseInt(account));
            openIdService.openIdUnbindAllPlatform(Integer.parseInt(account));
        }

    }
}
