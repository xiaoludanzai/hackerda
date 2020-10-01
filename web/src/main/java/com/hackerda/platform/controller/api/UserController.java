package com.hackerda.platform.controller.api;

import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.controller.request.CreateUserByStudentRequest;
import com.hackerda.platform.controller.request.ModifyUserInfoRequest;
import com.hackerda.platform.controller.vo.AppUserVO;
import com.hackerda.platform.domain.constant.ErrorCode;
import com.hackerda.platform.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/crateUserByStudent")
    public WebResponse<?> crateUserByStudent(@Valid CreateUserByStudentRequest request, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {

            List<String> list = bindingResult.getFieldErrors().stream()
                    .map(x -> x.getField() + x.getDefaultMessage())
                    .collect(Collectors.toList());

            return WebResponse.fail(ErrorCode.DATA_NOT_VALID.getErrorCode(), String.join(",", list));
        }

        return WebResponse.success(userService.registerByStudent(request));
    }


    @PostMapping("/getByStudentAccount")
    public WebResponse<?> getUserByStudentAccount(@RequestParam(value = "studentAccount") String account) {
        AppUserVO user = userService.getUserByStudentAccount(account);
        if(user != null) {
            return WebResponse.success(user);
        }

        return WebResponse.fail(ErrorCode.ACCOUNT_MISS.getErrorCode(), "用户信息不存在");

    }

    @GetMapping("/getByUserName")
    public WebResponse<AppUserVO> getByUserName(@RequestParam(value = "userName") String userName) {

        return WebResponse.success(userService.getByUserName(userName));

    }


}
