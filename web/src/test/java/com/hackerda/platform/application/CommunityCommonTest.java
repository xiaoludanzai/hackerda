package com.hackerda.platform.application;

import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.domain.message.MessageRepository;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.AppStudentUserBO;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.domain.user.PhoneNumber;
import com.hackerda.platform.domain.wechat.WechatUser;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityCommonTest {

    @Autowired
    private CommunityPostApp communityPostApp;
    @Autowired
    private UserRegisterApp userRegisterApp;
    @Autowired
    private StudentBindApp studentBindApp;
    @Autowired
    private CommunityCommentApp communityCommentApp;
    @Autowired
    private MessageRepository messageRepository;



    private AppStudentUserBO appStudentUserBO;

    private AppStudentUserBO appStudentUserBO2;
    @Before
    public void init() {
        communityPostApp.setContentSecurityCheckService(new ContentSecurityCheckService() {
            @Override
            public boolean isSecurityContent(String content) {
                return true;
            }

            @Override
            public boolean isSecurityImage(byte[] image) {
                return true;
            }
        });

        StudentAccount studentAccount = new StudentAccount("2014025838");
        PhoneNumber phoneNumber = new PhoneNumber("17301086276");
        WechatUser wechatUser = new WechatUser("test_appId", "test_openid");

        appStudentUserBO = new AppStudentUserBO(studentAccount, "test2", "1", "test_avatarPath",
                phoneNumber, Gender.Woman,
                "test_introduction");
        studentBindApp.bindByOpenId(studentAccount, "1", wechatUser);
        userRegisterApp.register(appStudentUserBO, wechatUser);

        StudentAccount studentAccount2 = new StudentAccount("2017025838");
        PhoneNumber phoneNumber2 = new PhoneNumber("17301086277");
        WechatUser wechatUser2 = new WechatUser("test_appId", "test_openid2");

        appStudentUserBO2 = new AppStudentUserBO(studentAccount2, "test2", "1", "test_avatarPath",
                phoneNumber2, Gender.Woman,
                "test_introduction");

        studentBindApp.bindByOpenId(studentAccount2, "1", wechatUser2);
        userRegisterApp.register(appStudentUserBO2, wechatUser2);
    }

    @Test
    public void createPost() {

        List<ImageInfo> imageInfos = Lists.newArrayList(new ImageInfo("test1", "test1"), new ImageInfo("test2",
                "test2", RecordStatus.Release));
        PostBO postBO = new PostBO(appStudentUserBO.getUserName(), "测试内容", imageInfos, IdentityCategory.Community,
                "IPhone X");
        postBO.setStatus(RecordStatus.Release);

        communityPostApp.createPost(postBO);

//        assertThat(posterRepository.findByPostById(postBO.getId())).isEqualTo(postBO);

        CommentBO commentBO = new CommentBO(postBO.getId(), postBO.getUserName(), appStudentUserBO2.getUserName(), "测试评论", 1L, 1L,
                IdentityCategory.Community, appStudentUserBO.getUserName());
        CommentBO commentBO2 = new CommentBO(postBO.getId(), postBO.getUserName(), appStudentUserBO2.getUserName(), "测试评论1", 2L, 2L,
                IdentityCategory.Community, appStudentUserBO.getUserName());

        communityCommentApp.addComment(commentBO);
        communityCommentApp.addComment(commentBO2);



        LikeBO likeBO = new LikeBO();

        likeBO.setReplyUserName(commentBO2.getUserName());
        likeBO.setUserName(appStudentUserBO.getUserName());
        likeBO.setLikeType(LikeType.Comment);
        likeBO.setShow(true);
        likeBO.setTypeContentId(commentBO2.getId());
        likeBO.setLikeTime(new Date());

        communityCommentApp.addLike(likeBO);

        assertThat(messageRepository.findByUserName(appStudentUserBO2.getUserName(), null, 20).size()).isEqualTo(1);
        assertThat(messageRepository.findByUserName(appStudentUserBO.getUserName(), null, 20).size()).isEqualTo(2);

    }
}