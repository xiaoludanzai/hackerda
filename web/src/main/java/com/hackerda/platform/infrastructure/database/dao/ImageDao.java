package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.platform.infrastructure.database.mapper.ImageInfoMapper;
import com.hackerda.platform.infrastructure.database.mapper.ext.ImageInfoExtMapper;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ImageDao {
    @Autowired
    private ImageInfoExtMapper imageInfoExtMapper;

    public void insertBatch(List<ImageInfoDO> imageInfoDOList) {
        if(!CollectionUtils.isEmpty(imageInfoDOList)) {
            imageInfoExtMapper.insertBatch(imageInfoDOList);
        }
    }

    public void insertPostImageRelative(Long postId, List<Long> imageIdList) {
        if(!CollectionUtils.isEmpty(imageIdList)) {
            imageInfoExtMapper.insertPostImageRelative(postId, imageIdList);
        }
    }

    public List<ImageInfoDO> selectByPostId(Long postId) {
        return imageInfoExtMapper.selectByPostId(postId);
    }

}
