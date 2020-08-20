package com.hackerda.platform.infrastructure.database.mapper;

import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageInfoMapper {
    long countByExample(ImageInfoDOExample example);

    int deleteByExample(ImageInfoDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImageInfoDO record);

    int insertSelective(ImageInfoDO record);

    List<ImageInfoDO> selectByExample(ImageInfoDOExample example);

    ImageInfoDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImageInfoDO record, @Param("example") ImageInfoDOExample example);

    int updateByExample(@Param("record") ImageInfoDO record, @Param("example") ImageInfoDOExample example);

    int updateByPrimaryKeySelective(ImageInfoDO record);

    int updateByPrimaryKey(ImageInfoDO record);

    int insertBatch(List<ImageInfoDO> recordList);

    void insertPostImageRelative(Long postId, List<Long> imageIdList);

    List<ImageInfoDO> selectByPostId(Long postId);
}