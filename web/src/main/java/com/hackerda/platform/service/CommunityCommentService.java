package com.hackerda.platform.service;

import com.hackerda.platform.application.CommunityCommentApp;
import com.hackerda.platform.application.event.EventPublisher;
import com.hackerda.platform.controller.request.LikeRequest;
import com.hackerda.platform.controller.request.CreateCommentRequest;
import com.hackerda.platform.controller.vo.CommentDetailVO;
import com.hackerda.platform.controller.vo.CommentVO;
import com.hackerda.platform.controller.vo.CreateCommentResultVO;
import com.hackerda.platform.controller.vo.PostUserVO;
import com.hackerda.platform.domain.community.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommunityCommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommunityCommentApp communityCommentApp;
    @Autowired
    private LikeCountService likeCountService;


    public CreateCommentResultVO addComment(String userName, CreateCommentRequest request){

        CommentBO commentBO = new CommentBO(request.getPostId(), request.getPostUserName(), userName,
                request.getContent(), Optional.ofNullable(request.getReplyCommentId()).orElse(0L),
                Optional.ofNullable(request.getRootCommentId()).orElse(0L),
                IdentityCategory.getByCode(request.getIdentityCode()), request.getReplyUserName());

        communityCommentApp.addComment(commentBO);

        CreateCommentResultVO vo = new CreateCommentResultVO();
        vo.setRelease(commentBO.isRelease());
        vo.setErrMsg(commentBO.getUnReleaseReason());

        return vo;
    }

    public CommentDetailVO findByPostId(String userName, long postId) {

        List<CommentDetailBO> detailByPostId = commentRepository.find(RecordStatus.Release, postId);
        Map<String, PostUserVO> userNamePosterMap = detailByPostId.stream()
                .collect(Collectors.toMap(CommentDetailBO::getUserName, this::toPostUserVO, (x1, x2) -> x1));

        Map<Long, CommentDetailBO> idMap = detailByPostId.stream()
                .collect(Collectors.toMap(CommentDetailBO::getId, x -> x));

        List<CommentVO> commentList = detailByPostId.stream().map(x -> {
            CommentVO commentVO = new CommentVO();
            commentVO.setContent(x.getContent());
            commentVO.setRootCommentId(x.getRootCommentId());
            commentVO.setReplyCommentId(x.getReplyCommentId());
            commentVO.setRoot(x.isRoot());
            commentVO.setId(x.getId());
            commentVO.setPostId(x.getPostId());
            commentVO.setPostTime(x.getPostTime());
            commentVO.setLikeCount(x.getLikeCount());
            commentVO.setPostUser(userNamePosterMap.get(x.getUserName()));
            
            if(!x.isRoot()) {
                CommentDetailBO replyDetailBO = idMap.get(x.getReplyCommentId());
                commentVO.setReplyUser(userNamePosterMap.get(replyDetailBO.getUserName()));
            }

            if(!userName.equals("guest")) {
                commentVO.setHasLike(likeCountService.hasLike(LikeType.Comment, x.getId(), userName));
            }

            long size = likeCountService.likeCount(LikeType.Comment, x.getId());
            commentVO.setLikeCount(size);
            return commentVO;
        }).collect(Collectors.toList());

        return new CommentDetailVO(commentList);

    }


    public CreateCommentResultVO addLike(LikeRequest likeRequest, String userName) {
        LikeBO likeBO = new LikeBO();
        likeBO.setShow(likeRequest.isAdd());
        likeBO.setLikeTime(new Date());
        likeBO.setLikeType(LikeType.getByCode(likeRequest.getContentType()));
        likeBO.setTypeContentId(likeRequest.getContentId());
        likeBO.setUserName(userName);
        likeBO.setReplyUserName(likeBO.getReplyUserName());

        CreateCommentResultVO vo = new CreateCommentResultVO();
        try {
            communityCommentApp.addLike(likeBO);
            vo.setRelease(true);
        }catch (Throwable throwable) {
            log.error("{} add like error {}",userName, likeRequest);
            vo.setRelease(false);
            vo.setErrMsg(throwable.getMessage());
        }

        return vo;
    }


    private PostUserVO toPostUserVO (CommentDetailBO commentDetailBO) {
        PostUserVO postUserVO = new PostUserVO();
        postUserVO.setAvatar(commentDetailBO.getShowAvatar());
        postUserVO.setShowUserName(commentDetailBO.getShowUserName());
        postUserVO.setShowUserNameOrder(commentDetailBO.getUserShowNameOrder());
        postUserVO.setUserName(commentDetailBO.getUserName());
        postUserVO.setPostAuthor(commentDetailBO.isSelfComment());

        return postUserVO;
    }

}
