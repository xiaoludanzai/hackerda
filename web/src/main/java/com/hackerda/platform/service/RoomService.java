package com.hackerda.platform.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hackerda.platform.infrastructure.dao.UrpClassRoomDao;
import com.hackerda.platform.pojo.UrpClassroom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author junrong.chen
 * @date 2018/10/30
 */
@Service
@Slf4j
public class RoomService {



	@Resource
	private UrpClassRoomDao urpClassRoomDao;


	private static final Cache<String, UrpClassroom> urpRoomCache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.build();

	public UrpClassroom getClassRoomByName(String name){
		try {
			return urpRoomCache.get(name, (() -> urpClassRoomDao.selectByName(name)));
		} catch (ExecutionException e) {
			log.error("get urp classroom form cache error", e);
			throw new RuntimeException(e);
		}


	}

}
