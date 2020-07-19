package com.hackerda.platform.repository;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FetchStatusRecorder {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean needToFetch(FetchScene fetchScene, String account){
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();

        return BooleanUtils.toBoolean(opsForSet.isMember(fetchScene.getKey(), account));
    }


    public void recordFinish(FetchScene fetchScene, String account){

        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();

        opsForSet.add(fetchScene.getKey(), account);
    }


    public void removeRecord(FetchScene fetchScene, String account){

        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();

        opsForSet.remove(fetchScene.getKey(), account);
    }


}
