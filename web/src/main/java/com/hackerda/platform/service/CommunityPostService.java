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
                IdentityCategory.getByCode(createPostRequest.getIdentityCode()));

        postBO.setStatus(RecordStatus.Create);

        communityPostApp.createPost(postBO);

        CreatePostResultVO createPostResultVO = new CreatePostResultVO();

        createPostResultVO.setRelease(postBO.isRelease());
        createPostResultVO.setErrMsg(postBO.getUnReleaseReason());

        return createPostResultVO;
    }


    public PostVO getPostById(int postId) {

        PostDetailBO post = posterRepository.findByPostById(postId);

        return getPostVO(post);
    }

    private PostVO getPostVO(PostDetailBO post) {
        PostVO postVO = new PostVO();

        postVO.setId(post.getId())
                .setContent(post.getContent())
                .setAllowComment(post.isAllowComment())
                .setAvatar(post.getShowAvatar())
                .setUserName(post.getUserName())
                .setViewCount(post.getViewCount())
                .setCommentCount(post.getCommentCount())
                .setLikeCount(post.getLikeCount())
                .setShowUserName(post.getShowUserName())
                .setPostTime(post.getPostTime());

        List<ImageInfoVO> imageInfoVOList = post.getImageInfoList().stream().map(imageInfo -> {
            ImageInfoVO infoVO = new ImageInfoVO();
            infoVO.setFileId(imageInfo.getFileId());
            infoVO.setUrl(imageInfo.getPath());
            return infoVO;
        }).collect(Collectors.toList());

        postVO.setImageInfoList(imageInfoVOList);

        return postVO;
    }

    public PostDetailVO getPostDetail(Integer startId, int count) {
        List<PostDetailBO> detailBOList = posterRepository.findShowPost(startId, count);

        List<PostVO> postVOList = detailBOList.stream()
                .map(this::getPostVO)
                .sorted(Comparator.comparing(PostVO :: getPostTime).reversed())
                .collect(Collectors.toList());


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
