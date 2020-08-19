package com.hackerda.platform.service;

import com.hackerda.platform.application.CommunityPostApp;
import com.hackerda.platform.controller.request.CreatePostRequest;
import com.hackerda.platform.controller.vo.CreatePostResultVO;
import com.hackerda.platform.controller.vo.PostIdentityVO;
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
}
