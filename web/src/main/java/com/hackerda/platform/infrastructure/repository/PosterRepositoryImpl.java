package com.hackerda.platform.infrastructure.repository;

import com.hackerda.platform.domain.community.PostBO;
import com.hackerda.platform.domain.community.PostDetailBO;
import com.hackerda.platform.domain.community.PosterRepository;
import com.hackerda.platform.domain.community.StudentPoster;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.infrastructure.database.dao.ImageDao;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.mapper.PostMapper;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import com.hackerda.platform.infrastructure.database.model.Post;
import com.hackerda.platform.infrastructure.database.model.StudentPosterDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PosterRepositoryImpl implements PosterRepository {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private PostMapper postMapper;

    @Override
    public StudentPoster findByStudentAccount(StudentAccount studentAccount) {

        StudentPosterDO studentPosterDO = userDao.selectByStudentPoster(studentAccount.getAccount());

        StudentPoster.StudentPosterBuilder builder = StudentPoster.builder(studentPosterDO.getUserName(),
                studentPosterDO.getNickName(), studentPosterDO.getAvatarUrl());

        builder.academyName(studentPosterDO.getAcademyName());
        builder.className(studentPosterDO.getClassName());
        builder.name(studentPosterDO.getName());
        builder.sex(studentPosterDO.getSex());
        builder.subjectName(studentPosterDO.getSubjectName());
        builder.studentAccount(studentAccount);
        builder.urpClassNum(studentPosterDO.getUrpClassNum());

        return builder.build();
    }

    @Override
    @Transactional
    public void save(PostBO postBO) {

        Post post = new Post();

        post.setAllowComment(postBO.isAllowComment() ? (byte) 1 : (byte) 0);
        post.setContent(postBO.getContent());
        post.setIdentityCode(postBO.getIdentityCategory().getCode());
        post.setStatus(postBO.getStatus().getCode());
        post.setUserName(postBO.getUserName());
        post.setPostTime(postBO.getPostTime());

        postMapper.insertSelective(post);

        List<ImageInfoDO> imageInfoDOList = postBO.getImageInfoList().stream().map(x -> {
            ImageInfoDO imageInfoDO = new ImageInfoDO();
            imageInfoDO.setFileId(x.getFileId());
            imageInfoDO.setUrl(x.getPath());
            return imageInfoDO;
        }).collect(Collectors.toList());

        imageDao.insertBatch(imageInfoDOList);

        imageDao.insertPostImageRelative(post.getId(), imageInfoDOList.stream()
                .map(ImageInfoDO::getId).collect(Collectors.toList()));


    }

    @Override
    public PostDetailBO findByPostById(long id) {
        return null;
    }


}
