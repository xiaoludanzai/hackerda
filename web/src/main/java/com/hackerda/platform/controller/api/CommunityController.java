package com.hackerda.platform.controller.api;

import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityController {

    @Autowired
    private CommunityPostService communityPostService;

    @GetMapping("/getPostIdentityByStudent")
    public WebResponse<?> getPostIdentityByStudent(@RequestParam(value = "studentAccount") String account){
        return WebResponse.success(communityPostService.getPostIdentityByStudent(account));
    }
}
