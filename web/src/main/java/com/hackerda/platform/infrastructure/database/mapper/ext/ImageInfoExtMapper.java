package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.ImageInfoMapper;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImageInfoExtMapper extends ImageInfoMapper {

    void insertPostImageRelative(Long postId, List<Long> imageIdList);

    List<ImageInfoDO> selectByPostId(Long postId);

    void insertBatch(List<ImageInfoDO> list);

}
