package com.hackerda.platform.infrastructure.spider;

import com.hackerda.platform.infrastructure.database.dao.StudentUserDao;
import com.hackerda.platform.infrastructure.database.mapper.WechatOpenidStudentRelativeMapper;
import com.hackerda.platform.infrastructure.database.model.WechatOpenidStudentRelativeExample;
import com.hackerda.platform.service.OpenIdService;
import com.hackerda.spider.IExceptionHandler;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import org.springframework.stereotype.Component;

@Component
public class SpiderExceptionHandler implements IExceptionHandler {

    private final StudentUserDao studentUserDao;

    private final WechatOpenidStudentRelativeMapper wechatOpenidStudentRelativeMapper;

    public SpiderExceptionHandler(StudentUserDao studentUserDao, WechatOpenidStudentRelativeMapper wechatOpenidStudentRelativeMapper) {
        this.studentUserDao = studentUserDao;
        this.wechatOpenidStudentRelativeMapper = wechatOpenidStudentRelativeMapper;
    }


    @Override
    public void handle(Exception e, String account) {

        if(e instanceof PasswordUnCorrectException){
            studentUserDao.updatePasswordUnCorrect(Integer.parseInt(account));

            WechatOpenidStudentRelativeExample example = new WechatOpenidStudentRelativeExample();
            example.createCriteria().andAccountEqualTo(Integer.parseInt(account));

            wechatOpenidStudentRelativeMapper.deleteByExample(example);
        }

    }
}
