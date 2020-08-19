package com.hackerda.platform.controller.auth;

import org.apache.shiro.authc.AuthenticationToken;

public class UserJWTToken implements AuthenticationToken {

    private final String token;

    public UserJWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
