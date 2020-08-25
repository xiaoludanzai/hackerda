package com.hackerda.platform.controller.api;

import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.controller.request.CreateCommentRequest;
import com.hackerda.platform.controller.request.CreatePostRequest;
import com.hackerda.platform.controller.request.LikeRequest;
import com.hackerda.platform.controller.vo.CommentDetailVO;
import com.hackerda.platform.controller.vo.CreateCommentResultVO;
import com.hackerda.platform.controller.vo.PostDetailVO;
import com.hackerda.platform.controller.vo.PostVO;
import com.hackerda.platform.service.CommunityCommentService;
import com.hackerda.platform.service.CommunityPostService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityCommentService communityCommentService;

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

    @GetMapping("/getPostById")
    public WebResponse<PostVO> getPost(@RequestParam(value = "postId") int postId){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return WebResponse.success(communityPostService.getPostById(username, postId));
    }

    @GetMapping("/getPost")
    public WebResponse<PostDetailVO> getPost(@RequestParam(value = "startId", required = false) Integer startId,
                                                @RequestParam(value = "count") int count){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return WebResponse.success(communityPostService.getPostDetail(username, startId, count));
    }

    @RequiresAuthentication
    @PostMapping("/createComment")
    public WebResponse<CreateCommentResultVO> createComment(@RequestBody CreateCommentRequest createCommentRequest){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return WebResponse.success(communityCommentService.addComment(username, createCommentRequest));
    }

    @GetMapping("/getCommentByPostId")
    public WebResponse<CommentDetailVO> getDetailByPostId(@RequestParam(value = "postId") Long postId){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return WebResponse.success(communityCommentService.findByPostId(username, postId));
    }


    @GetMapping("/addLike")
    public WebResponse<CreateCommentResultVO> addLike(LikeRequest likeRequest){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return WebResponse.success(communityCommentService.addLike(likeRequest, username));
    }
}
