package com.hackerda.platform.config;

import com.google.common.collect.Lists;
import com.hackerda.platform.controller.auth.*;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.user.UserRepository;
import com.hackerda.platform.service.rbac.UserDetailService;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjuanrong
 */
@Configuration
public class ShiroConfig {

    @Bean
    public StudentJWTRealm studentJWTRealm(UserDetailService userDetailService, StudentRepository studentRepository){
        StudentJWTRealm studentJwtRealm = new StudentJWTRealm(userDetailService, studentRepository);
        studentJwtRealm.setAuthenticationTokenClass(JWTToken.class);
        return studentJwtRealm;
    }

    @Bean
    public UserJWTRealm userJWTRealm(UserRepository userRepository){
        UserJWTRealm studentJwtRealm = new UserJWTRealm(userRepository);
        studentJwtRealm.setAuthenticationTokenClass(UserJWTToken.class);
        return studentJwtRealm;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(StudentJWTRealm studentJWTRealm, UserJWTRealm userJWTRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm

        List<Realm> realms = Lists.newArrayList(studentJWTRealm, userJWTRealm);
        manager.setRealms(realms);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();

        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        modularRealmAuthenticator.setRealms(realms);
        manager.setAuthenticator(modularRealmAuthenticator);

        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(4);
        filterMap.put(RestFilter.NAME, new RestFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");

        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new HashMap<>(4);
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/api/**", RestFilter.NAME);
        filterRuleMap.put("/community/**", RestFilter.NAME);
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
