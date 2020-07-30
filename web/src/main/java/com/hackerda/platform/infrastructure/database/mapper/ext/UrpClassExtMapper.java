package com.hackerda.platform.infrastructure.database.mapper.ext;

import com.hackerda.platform.infrastructure.database.mapper.UrpClassMapper;
import com.hackerda.platform.infrastructure.database.model.UrpClass;
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
