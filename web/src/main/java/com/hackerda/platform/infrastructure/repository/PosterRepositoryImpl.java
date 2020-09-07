package com.hackerda.platform.infrastructure.repository;

import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.Gender;
import com.hackerda.platform.infrastructure.database.dao.ImageDao;
import com.hackerda.platform.infrastructure.database.dao.user.UserDao;
import com.hackerda.platform.infrastructure.database.mapper.ext.PostExtMapper;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import com.hackerda.platform.infrastructure.database.model.Post;
import com.hackerda.platform.infrastructure.database.model.PostExample;
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
    private PostExtMapper postExtMapper;

    @Override
    public StudentPoster findByStudentAccount(StudentAccount studentAccount) {

        StudentPosterDO studentPosterDO = userDao.selectByStudentPoster(studentAccount.getAccount());

        return getStudentPoster(studentPosterDO, studentAccount);
    }

    @Override
    public StudentPoster findStudentPosterByUserName(String userName) {
        StudentPosterDO studentPosterDO = userDao.selectStudentPosterByUserName(userName);
        StudentAccount studentAccount = new StudentAccount(studentPosterDO.getAccount());
        return getStudentPoster(studentPosterDO, studentAccount);
    }

    private StudentPoster getStudentPoster(StudentPosterDO studentPosterDO, StudentAccount studentAccount) {
        StudentPoster.StudentPosterBuilder builder = StudentPoster.builder(studentPosterDO.getUserName(),
                studentPosterDO.getNickName(), studentPosterDO.getAvatarUrl(), Gender.formCode(studentPosterDO.getGender()));

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

        Post post = adapter(postBO);
        postExtMapper.insertSelective(post);

        List<ImageInfoDO> imageInfoDOList = postBO.getImageInfoList().stream().map(x -> {
            ImageInfoDO imageInfoDO = new ImageInfoDO();
            imageInfoDO.setFileId(x.getFileId());
            imageInfoDO.setUrl(x.getPath());
            imageInfoDO.setRecordStatus(x.getRecordStatus().getCode());
            return imageInfoDO;
        }).collect(Collectors.toList());

        imageDao.insertBatch(imageInfoDOList);

        imageDao.insertPostImageRelative(post.getId(), imageInfoDOList.stream()
                .map(ImageInfoDO::getId).collect(Collectors.toList()));


    }

    @Override
    public void update(PostBO postBO, long id) {
        Post post = adapter(postBO);

        post.setId(id);

        postExtMapper.updateByPrimaryKeySelective(post);
    }

    private Post adapter(PostBO postBO) {
        Post post = new Post();

        post.setAllowComment(postBO.isAllowComment() ? (byte) 1 : (byte) 0);
        post.setContent(postBO.getContent());
        post.setIdentityCode(postBO.getIdentityCategory().getCode());
        post.setRecordStatus(postBO.getStatus().getCode());
        post.setUserName(postBO.getUserName());
        post.setPostTime(postBO.getPostTime());
        post.setEquipment(postBO.getEquipment());
        return post;
    }

    @Override
    public PostDetailBO findByPostById(long id) {
        Post post = postExtMapper.selectByPrimaryKey(id);

        return getPostDetailBO(post);
    }



    @Override
    public List<PostDetailBO> findShowPost(Integer startId, int limit) {

        List<Post> postList = postExtMapper.selectShowPost(startId, limit);

        return postList.stream().map(this::getPostDetailBO).collect(Collectors.toList());

    }

    @Override
    public long count() {
        return postExtMapper.countByExample(new PostExample());
    }

    private PostDetailBO getPostDetailBO(Post post) {
        List<ImageInfo> imageInfoList = imageDao.selectByPostId(post.getId()).stream()
                .map(imageInfoDO -> new ImageInfo(imageInfoDO.getUrl(), imageInfoDO.getFileId())).collect(Collectors.toList());

        PostDetailBO postDetailBO = new PostDetailBO(post.getId(), post.getUserName(), post.getContent(), imageInfoList,
                IdentityCategory.getByCode(post.getIdentityCode()), post.getPostTime(), post.getEquipment());

        StudentPoster poster = this.findStudentPosterByUserName(post.getUserName());

        postDetailBO.setCommentCount(post.getCommentCount());
        postDetailBO.setViewCount(post.getViewCount());
        postDetailBO.setLikeCount(post.getLikeCount());
        postDetailBO.setPostUser(poster);
        postDetailBO.setStatus(RecordStatus.getByCode(post.getRecordStatus()));
        return postDetailBO;
    }
}
