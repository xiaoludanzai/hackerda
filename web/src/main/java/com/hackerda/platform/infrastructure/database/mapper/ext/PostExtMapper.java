package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.PostMapper;
import com.hackerda.platform.infrastructure.database.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostExtMapper extends PostMapper {

    List<Post> selectShowPost(Integer startId, int count);
}
