package com.hackerda.platform.service;

import com.hackerda.platform.application.CommunityPostApp;
import com.hackerda.platform.controller.request.CreatePostRequest;
import com.hackerda.platform.controller.vo.*;
import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.domain.student.StudentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityPostService {

    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private CommunityPostApp communityPostApp;
    @Autowired
    private LikeCountService likeCountService;
    @Autowired
    private CommentCountService commentCountService;

    public PostIdentityVO getPostIdentityByStudent(String account) {

        StudentAccount studentAccount = new StudentAccount(account);

        PostIdentityVO vo = new PostIdentityVO();

        StudentPoster studentPoster = posterRepository.findByStudentAccount(studentAccount);

        if(studentPoster != null) {
            for (IdentityCategory value : IdentityCategory.values()) {

                boolean checked =  value == IdentityCategory.Community;
                vo.add(value.getCode(), studentPoster.getShowName(value), studentPoster.getShowAvatarUrl(value), checked);
            }
        }
        return vo;
    }

    public CreatePostResultVO cratePost(String userName, CreatePostRequest createPostRequest) {
        List<ImageInfo> imageInfoList = createPostRequest.getImageInfoRequestList().stream()
                .map(x -> new ImageInfo(x.getTempFileURL(), x.getFileID()))
                .collect(Collectors.toList());


        PostBO postBO = new PostBO(userName, createPostRequest.getContent(), imageInfoList,
                IdentityCategory.getByCode(createPostRequest.getIdentityCode()), createPostRequest.getModel());

        postBO.setStatus(RecordStatus.Create);

        communityPostApp.createPost(postBO);

        CreatePostResultVO createPostResultVO = new CreatePostResultVO();

        createPostResultVO.setRelease(postBO.isRelease());
        createPostResultVO.setErrMsg(postBO.getUnReleaseReason());

        return createPostResultVO;
    }


    public PostVO getPostById(String userName, int postId) {

        PostDetailBO post = posterRepository.findByPostById(postId);
        PostVO postVO = getPostVO(post);
        setLikeCount(userName, postVO);
        return postVO;
    }

    private void setLikeCount(String userName, PostVO postVO) {
        if(!userName.equals("guest")) {
            boolean hasLike = likeCountService.hasLike(LikeType.Post, postVO.getId(), userName);
            postVO.setHasLike(hasLike);
        }
        long size = likeCountService.likeCount(LikeType.Post, postVO.getId());
        postVO.setLikeCount(size);
    }

    private PostVO getPostVO(PostDetailBO post) {
        PostVO postVO = new PostVO();

        postVO.setId(post.getId())
                .setContent(post.getContent())
                .setAllowComment(post.isAllowComment())
                .setAvatar(post.getShowAvatar())
                .setUserName(post.getUserName())
                .setViewCount(post.getViewCount())
                .setCommentCount(commentCountService.count(post.getId()))
                .setLikeCount(post.getLikeCount())
                .setShowUserName(post.getShowUserName())
                .setPostTime(post.getPostTime())
                .setIdentityCode(post.getIdentityCategory().getCode());

        List<ImageInfoVO> imageInfoVOList = post.getImageInfoList().stream().map(imageInfo -> {
            ImageInfoVO infoVO = new ImageInfoVO();
            infoVO.setFileId(imageInfo.getFileId());
            infoVO.setUrl(imageInfo.getPath());
            return infoVO;
        }).collect(Collectors.toList());

        postVO.setImageInfoList(imageInfoVOList);

        return postVO;
    }

    public PostDetailVO getPostDetail(String userName, Integer startId, int count) {
        List<PostDetailBO> detailBOList = posterRepository.findShowPost(startId, count);

        List<PostVO> postVOList = detailBOList.stream()
                .map(this::getPostVO)
                .sorted(Comparator.comparing(PostVO :: getPostTime).reversed())
                .collect(Collectors.toList());

        for (PostVO postVO : postVOList) {
            setLikeCount(userName, postVO);
        }

        PostDetailVO postDetailVO = new PostDetailVO();

        postDetailVO.setPostList(postVOList);

        postDetailVO.setCount(postVOList.size());

        long nextMaxId = postVOList.stream()
                .map(PostVO::getId)
                .min(Long::compareUnsigned)
                .orElse(-1L);

        postDetailVO.setNextMaxId(nextMaxId);

        return postDetailVO;

    }
}
