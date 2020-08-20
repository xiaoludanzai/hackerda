package com.hackerda.platform.service;

import com.hackerda.platform.application.CommunityPostApp;
import com.hackerda.platform.controller.request.CreatePostRequest;
import com.hackerda.platform.controller.vo.*;
import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.domain.student.StudentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public PostDetailVO getPostDetail(int startId, int count) {
        List<PostDetailBO> detailBOList = posterRepository.findShowPost(startId, count);

        List<PostVO> postVOList = detailBOList.stream().map(x -> {

            PostVO postVO = new PostVO();

            postVO.setId(x.getId())
                    .setContent(x.getContent())
                    .setAllowComment(x.isAllowComment())
                    .setAvatar(x.getShowAvatar())
                    .setUserName(x.getUserName())
                    .setViewCount(x.getViewCount())
                    .setCommentCount(x.getCommentCount())
                    .setLikeCount(x.getLikeCount())
                    .setShowUserName(x.getShowUserName())
                    .setPostTime(x.getPostTime());

            List<ImageInfoVO> imageInfoVOList = x.getImageInfoList().stream().map(imageInfo -> {
                ImageInfoVO infoVO = new ImageInfoVO();
                infoVO.setFileId(imageInfo.getFileId());
                infoVO.setUrl(imageInfo.getPath());
                return infoVO;
            }).collect(Collectors.toList());

            postVO.setImageInfoList(imageInfoVOList);

            return postVO;

        }).collect(Collectors.toList());


        PostDetailVO postDetailVO = new PostDetailVO();

        postDetailVO.setPostList(postVOList);

        postDetailVO.setCount(postVOList.size());

        Long maxId = postVOList.stream()
                .map(PostVO::getId)
                .max(Long::compareUnsigned)
                .orElse(-1L);

        postDetailVO.setMaxId(maxId);

        return postDetailVO;

    }
}
