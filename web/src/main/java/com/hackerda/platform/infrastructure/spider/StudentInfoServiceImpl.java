package com.hackerda.platform.infrastructure.spider;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.*;
import com.hackerda.platform.domain.user.LifeCycleStatus;
import com.hackerda.platform.domain.user.UserRegisterAssist;
import com.hackerda.platform.domain.user.UserRegisterRecordBO;
import com.hackerda.platform.domain.user.UserRegisterRecordRepository;
import com.hackerda.platform.domain.wechat.ActionRecord;
import com.hackerda.platform.domain.wechat.WechatActionRecordRepository;
import com.hackerda.platform.domain.wechat.WechatUser;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.dao.UrpClassDao;
import com.hackerda.platform.infrastructure.database.dao.WechatOpenIdDao;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.mapper.UserRegisterRecordMapper;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.UrpClass;
import com.hackerda.platform.infrastructure.database.model.User;
import com.hackerda.platform.infrastructure.database.model.UserRegisterRecord;
import com.hackerda.platform.service.NewUrpSpiderService;


import com.hackerda.spider.UrpSearchSpider;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpTimeoutException;
import com.hackerda.spider.support.search.classInfo.ClassInfoSearchResult;
import com.hackerda.spider.support.search.classInfo.SearchClassInfoPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentInfoServiceImpl implements StudentInfoService {
    @Autowired
    private NewUrpSpiderService newUrpSpiderService;
    @Autowired
    private UrpClassDao urpClassDao;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UrpSearchSpider urpSearchSpider;
    @Autowired
    private SpiderExceptionTransfer exceptionTransfer;


    @Override
    public boolean checkPasswordValid(String account, String enablePassword) {
        try {
            newUrpSpiderService.checkStudentPassword(account, enablePassword);
            return true;
        }catch (PasswordUnCorrectException e) {
            return false;
        } catch (UrpTimeoutException | ResourceAccessException e) {
            StudentUserBO studentUserBO = studentRepository.find(new StudentAccount(account));
            if (studentUserBO == null) {
                throw new BusinessException(ErrorCode.READ_TIMEOUT, "读取验证码超时");
            }
            return studentUserBO.getIsCorrect();
        }

        catch (Throwable throwable){
            throw exceptionTransfer.transfer(throwable);
        }
    }


    @Override
    public WechatStudentUserBO getStudentInfo(StudentAccount account, String enablePassword) {

        StudentUser userInfo = newUrpSpiderService.getStudentUserInfo(account.getAccount(), enablePassword);
        UrpClass urpClass = getClassByName(userInfo.getClassName(), userInfo.getAccount().toString());

        WechatStudentUserBO user = new WechatStudentUserBO();

        user.setAccount(new StudentAccount(userInfo.getAccount()));
        user.setPassword(userInfo.getPassword());
        user.setIsCorrect(userInfo.getIsCorrect());
        user.setUrpClassNum(Integer.parseInt(urpClass.getClassNum()));
        user.setAcademyName(userInfo.getAcademyName());
        user.setClassName(userInfo.getClassName());
        user.setEthnic(userInfo.getEthnic());
        user.setSubjectName(userInfo.getSubjectName());
        user.setSex(userInfo.getSex());
        user.setName(userInfo.getName());
        user.setMsgHasCheck(true);
        user.setSaveOrUpdate(true);

        return user;
    }


    public UrpClass getClassByName(String className, String account){

        try{
            UrpClass urpClass = urpClassDao.selectByName(className);

            if (urpClass != null){
                return urpClass;
            }

            SearchClassInfoPost post = new SearchClassInfoPost();
            String start = account.substring(0, 4);
            post.setYearNum(start);

            List<UrpClass> results = searchUrpClass(post);
            Map<String, UrpClass> collect = results.stream()
                    .collect(Collectors.toMap(UrpClass::getClassName, x -> x, (k1, k2) -> k1));

            urpClassDao.insertBatch(new ArrayList<>(collect.values()));

            return collect.get(className);
        }catch (Exception e){
            log.error("get className {} exception", className, e);
            throw new RuntimeException(e);
        }

    }

    public List<UrpClass> searchUrpClass(SearchClassInfoPost searchClassInfoPost) {
        return urpSearchSpider.searchClassInfo(searchClassInfoPost).stream()
                .flatMap(x -> x.getRecords().stream())
                .map(this::transToUrpClass)
                .collect(Collectors.toList());


    }

    public UrpClass transToUrpClass(ClassInfoSearchResult classInfoSearchResult){
        return new UrpClass()
                .setAcademyName(classInfoSearchResult.getDepartmentName())
                .setAcademyNum(classInfoSearchResult.getDepartmentNum())
                .setClassName(classInfoSearchResult.getClassName().endsWith("班")? classInfoSearchResult.getClassName().substring(0,
                        classInfoSearchResult.getClassName().length()-1) : classInfoSearchResult.getClassName())
                .setClassNum(classInfoSearchResult.getId().getClassNum())
                .setSubjectName(classInfoSearchResult.getSubjectName())
                .setSubjectNum(classInfoSearchResult.getSubjectNum())
                .setAdmissionGrade(classInfoSearchResult.getAdmissionGrade());
    }
}
