package com.hackerda.platform.controller.auth;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.infrastructure.database.model.Permission;
import com.hackerda.platform.infrastructure.database.model.Role;
import com.hackerda.platform.infrastructure.database.model.UserDetail;
import com.hackerda.platform.service.rbac.UserDetailService;
import com.hackerda.platform.utils.JwtUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.stream.Collectors;

/**
 * @author chenjuanrong
 */
@Slf4j
public class StudentJWTRealm extends AuthorizingRealm {

    private final UserDetailService userDetailService;
    private final StudentRepository studentRepository;

    public StudentJWTRealm(UserDetailService userDetailService, StudentRepository studentRepository){
        this.userDetailService = userDetailService;
        this.studentRepository = studentRepository;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * 我们可以直接查库来获取用户的权限，如果希望减少查询开销对实时性要求不高
     * 也可以将用户角色和权限写在payGround上来获取，配合JWT的过期机制定时更新
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = principals.toString();

        UserDetail userDetail = userDetailService.getStudentUserDetail(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(userDetail.getRoleSet().stream().map(Role::getCode).collect(Collectors.toSet()));

        simpleAuthorizationInfo.addStringPermissions(userDetail.getPermissionSet().stream().map(Permission::getPermissionCode).collect(Collectors.toSet()));
        return simpleAuthorizationInfo;
    }

    /**
     * 这个方法顾名思义，获取授权的认证信息，对于token的合法校验可以在这一步完成
     * 也可以实现{@link CredentialsMatcher}接口来完成
     *
     * 这样做的细微差别是，如果你的获取用户信息是个IO密集的操作，
     * shiro可以为你缓存这个结果，直接由CredentialsMatcher接口来判断
     *
     * For most dataSources, this means just 'pulling' authentication data for an associated subject/user and nothing
     * more and letting Shiro do the rest.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比

        if(StringUtils.isEmpty(token)){
            return null;
        }
        String username = JwtUtils.getClaim(token, JwtUtils.USERNAME_KEY);
        String appId = JwtUtils.getClaim(token, "appId");
        if (username == null || appId == null) {
            throw new AuthenticationException("token invalid");
        }
        try {
            WechatStudentUserBO user = studentRepository.findWetChatUser(new StudentAccount(username));

            if(user == null || !user.getIsCorrect()) {
                log.error("student account {} verify error {}", username, user);
                return null;
            }
            JwtUtils.verify(token, username, user.getOpenid(appId));
            return new SimpleAuthenticationInfo(user, token, "JWTRealm");
        }catch (JWTVerificationException e){
            throw new AuthenticationException(e);
        }catch (Exception e){
            log.error("verify token error", e);
            throw new AuthenticationException(e);
        }



    }
}
