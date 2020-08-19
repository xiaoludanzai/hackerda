package com.hackerda.platform.controller.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hackerda.platform.domain.user.AppUserBO;
import com.hackerda.platform.domain.user.UserRepository;
import com.hackerda.platform.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class UserJWTRealm extends AuthorizingRealm {

    private final UserRepository userRepository;

    public UserJWTRealm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        if(StringUtils.isEmpty(token)){
            return null;
        }
        String username = JwtUtils.getClaim(token, JwtUtils.USERNAME_KEY);

        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        try {
            AppUserBO userBO = userRepository.findByUserName(username);
            if(userBO == null) {
                return null;
            }
            JwtUtils.verify(token, username, userBO.getSalt());
            return new SimpleAuthenticationInfo(username, token, "userJWTRealm");
        }catch (JWTVerificationException e){
            return null;
        }catch (Exception e){
            log.error("verify token error", e);
            throw new AuthenticationException(e);
        }
    }
}
