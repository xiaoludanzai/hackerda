package com.hackerda.platform.controller.api;

import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.controller.request.CreatePostRequest;
import com.hackerda.platform.controller.vo.PostDetailVO;
import com.hackerda.platform.service.CommunityPostService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityPostService communityPostService;

    @RequiresAuthentication
    @GetMapping("/getPostIdentityByStudent")
    public WebResponse<?> getPostIdentityByStudent(@RequestParam(value = "studentAccount") String account){
        return WebResponse.success(communityPostService.getPostIdentityByStudent(account));
    }

    @RequiresAuthentication
    @PostMapping("/createPost")
    public WebResponse<?> createPost(@RequestBody CreatePostRequest createPostRequest){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return WebResponse.success(communityPostService.cratePost(username, createPostRequest));
    }

    @GetMapping("/getPost")
    public WebResponse<PostDetailVO> getPost(@RequestParam(value = "startId") int startId,
                                                @RequestParam(value = "count") int count){
        return WebResponse.success(communityPostService.getPostDetail(startId, count));
    }
}