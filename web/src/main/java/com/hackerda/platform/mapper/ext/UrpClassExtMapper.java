package com.hackerda.platform.mapper.ext;

import com.hackerda.platform.mapper.UrpClassMapper;
import com.hackerda.platform.pojo.UrpClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JR Chan
 */
@Mapper
@Repository
public interface UrpClassExtMapper extends UrpClassMapper {
    void insertBatch(List<UrpClass> list);
}
