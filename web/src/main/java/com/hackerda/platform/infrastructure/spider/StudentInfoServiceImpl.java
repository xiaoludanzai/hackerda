package com.hackerda.platform.infrastructure.spider;

import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.domain.student.StudentInfoService;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.StudentUserRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.exception.BusinessException;
import com.hackerda.platform.infrastructure.database.dao.StudentUserDao;
import com.hackerda.platform.infrastructure.database.dao.UrpClassDao;
import com.hackerda.platform.infrastructure.database.dao.WechatOpenIdDao;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.infrastructure.database.model.UrpClass;
import com.hackerda.platform.infrastructure.database.model.WechatOpenid;
import com.hackerda.platform.service.NewUrpSpiderService;


import com.hackerda.spider.UrpSearchSpider;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpTimeoutException;
import com.hackerda.spider.support.search.classInfo.ClassInfoSearchResult;
import com.hackerda.spider.support.search.classInfo.SearchClassInfoPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private WechatOpenIdDao wechatOpenIdDao;
    @Autowired
    private UrpClassDao urpClassDao;
    @Autowired
    private StudentUserRepository studentUserRepository;
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
        } catch (UrpTimeoutException e) {

            StudentUserBO studentUserBO = studentUserRepository.getByAccount(Integer.parseInt(account));
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
    public boolean checkCanBind(String account, String appId, String openid) {

        WechatOpenid wechatOpenid = wechatOpenIdDao.selectBindUser(Integer.parseInt(account), appId);

        if(wechatOpenid == null) {
            return true;
        }else return wechatOpenid.getOpenid().equals(openid);
    }

    @Override
    public WechatStudentUserBO getStudentInfo(String account, String enablePassword) {

        StudentUser userInfo = newUrpSpiderService.getStudentUserInfo(account, enablePassword);
        UrpClass urpClass = getClassByName(userInfo.getClassName(), userInfo.getAccount().toString());

        WechatStudentUserBO user = new WechatStudentUserBO();

        user.setAccount(userInfo.getAccount());
        user.setPassword(userInfo.getPassword());
        user.setIsCorrect(userInfo.getIsCorrect());
        user.setUrpClassNum(Integer.parseInt(urpClass.getClassNum()));
        user.setAcademyName(userInfo.getAcademyName());
        user.setClassName(userInfo.getClassName());
        user.setEthnic(userInfo.getEthnic());
        user.setSubjectName(userInfo.getSubjectName());
        user.setSex(userInfo.getSex());
        user.setName(userInfo.getName());
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
            int end = Integer.parseInt(start) + 1;
            post.setYearNum(start);
            post.setExecutiveEducationPlanNum(start + "-"+ end + "-1-1");

            List<UrpClass> results = searchUrpClass(post);
            Map<String, UrpClass> collect = results.stream()
                    .collect(Collectors.toMap(UrpClass::getClassName, x -> x));


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
