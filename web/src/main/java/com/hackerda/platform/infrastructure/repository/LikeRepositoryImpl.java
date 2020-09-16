package com.hackerda.platform.infrastructure.repository;

import com.hackerda.platform.domain.community.LikeBO;
import com.hackerda.platform.domain.community.LikeRepository;
import com.hackerda.platform.domain.community.LikeType;
import com.hackerda.platform.infrastructure.database.mapper.AppreciateMapper;
import com.hackerda.platform.infrastructure.database.model.Appreciate;
import com.hackerda.platform.infrastructure.database.model.AppreciateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    @Autowired
    private AppreciateMapper appreciateMapper;

    @Override
    public void save(LikeBO likeBO) {
        Appreciate like = toDO(likeBO);

        appreciateMapper.insertSelective(like);

        likeBO.setId(like.getId());
    }

    private Appreciate toDO(LikeBO likeBO) {
        Appreciate like = new Appreciate();
        like.setLikeTime(likeBO.getLikeTime());
        like.setLikeType(likeBO.getLikeType().getCode());
        like.setUserName(likeBO.getUserName());
        like.setTypeContentId(likeBO.getTypeContentId());
        like.setShow(likeBO.isShow() ? (byte) 1 : (byte) 0);
        like.setId(likeBO.getId());
        like.setReplyUserName(likeBO.getReplyUserName());
        return like;
    }

    @Override
    public void update(LikeBO likeBO) {
        Appreciate like = toDO(likeBO);

        if(like.getId() != null) {
            appreciateMapper.updateByPrimaryKeySelective(like);
        }else {
            AppreciateExample example = new AppreciateExample();
            example.createCriteria()
                    .andUserNameEqualTo(likeBO.getUserName())
                    .andLikeTypeEqualTo(likeBO.getLikeType().getCode())
                    .andReplyUserNameEqualTo(likeBO.getReplyUserName())
                    .andTypeContentIdEqualTo(like.getTypeContentId());

            appreciateMapper.updateByExampleSelective(like, example);
        }


    }

    @Override
    public LikeBO find(String userName, LikeType likeType, long typeId) {

        AppreciateExample example = new AppreciateExample();
        example.createCriteria()
                .andUserNameEqualTo(userName)
                .andLikeTypeEqualTo(likeType.getCode())
                .andTypeContentIdEqualTo(typeId);

        Appreciate like = appreciateMapper.selectByExample(example).stream().findFirst().orElse(null);

        if(like != null) {
            return toBO(like);
        }

        return null;
    }

    @Override
    public List<LikeBO> findByReplyUserName(String userName) {
        AppreciateExample example = new AppreciateExample();
        example.createCriteria()
                .andReplyUserNameEqualTo(userName);
        return appreciateMapper.selectByExample(example).stream().map(this::toBO).collect(Collectors.toList());

    }

    @Override
    public List<LikeBO> findByIdList(List<Long> idList) {
        if(idList.isEmpty()) {
            return Collections.emptyList();
        }

        AppreciateExample example = new AppreciateExample();
        example.createCriteria()
                .andIdIn(idList);

        return appreciateMapper.selectByExample(example).stream().map(this::toBO).collect(Collectors.toList());
    }

    private LikeBO toBO(Appreciate like) {
        LikeBO likeBO = new LikeBO();
        likeBO.setUserName(like.getUserName());
        likeBO.setLikeTime(like.getLikeTime());
        likeBO.setLikeType(LikeType.getByCode(like.getLikeType()));
        likeBO.setTypeContentId(like.getTypeContentId());
        likeBO.setShow(like.getShow() == (byte) 1);
        likeBO.setId(like.getId());
        likeBO.setReplyUserName(like.getReplyUserName());
        return likeBO;
    }

    @Override
    public List<LikeBO> findAll(LikeType likeType, long typeId) {
        AppreciateExample example = new AppreciateExample();
        example.createCriteria()
                .andLikeTypeEqualTo(likeType.getCode())
                .andTypeContentIdEqualTo(typeId);
        return appreciateMapper.selectByExample(example).stream().map(this::toBO).collect(Collectors.toList());
    }

    @Override
    public List<LikeBO> findShow(LikeType likeType, long typeId) {
        AppreciateExample example = new AppreciateExample();
        example.createCriteria()
                .andLikeTypeEqualTo(likeType.getCode())
                .andShowEqualTo((byte) 1)
                .andTypeContentIdEqualTo(typeId);
        return appreciateMapper.selectByExample(example).stream().map(this::toBO).collect(Collectors.toList());
    }
}
