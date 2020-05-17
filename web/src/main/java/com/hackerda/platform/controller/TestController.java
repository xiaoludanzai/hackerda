package com.hackerda.platform.controller;

import com.hackerda.platform.pojo.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {


    @RequestMapping("tests")
    public String test(String echostr){
        return echostr;
    }

    @RequiresRoles(value = {"admin"})
    @RequestMapping("test")
    public String testPermission(String echostr){
        return echostr;
    }


    @RequiresRoles(value = {"admin"})
    @RequestMapping("/api/test")
    public String testPermissions(String echostr){
        return echostr;
    }

    @GetMapping("/api/require_auth")
    @RequiresAuthentication
    public WebResponse requireAuth() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return WebResponse.success(principal.toString());
    }

}
