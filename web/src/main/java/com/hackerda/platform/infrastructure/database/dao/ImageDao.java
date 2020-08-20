package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.ImageInfoMapper;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ImageDao {
    @Autowired
    private ImageInfoMapper imageInfoMapper;

    public void insertBatch(List<ImageInfoDO> imageInfoDOList) {
        if(!CollectionUtils.isEmpty(imageInfoDOList)) {
            imageInfoMapper.insertBatch(imageInfoDOList);
        }
    }

    public void insertPostImageRelative(Long postId, List<Long> imageIdList) {
        if(!CollectionUtils.isEmpty(imageIdList)) {
            imageInfoMapper.insertPostImageRelative(postId, imageIdList);
        }
    }

    public List<ImageInfoDO> selectByPostId(Long postId) {
        return imageInfoMapper.selectByPostId(postId);
    }

}
