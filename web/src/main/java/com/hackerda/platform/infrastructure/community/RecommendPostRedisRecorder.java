package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.domain.community.RecommendPostRecorder;
import com.hackerda.platform.domain.constant.RedisKeys;
import com.hackerda.platform.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendPostRedisRecorder implements RecommendPostRecorder {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public List<Long> getPostIdList(Date date) {

        return Optional.ofNullable(stringRedisTemplate.opsForSet().members(genKey(date))).orElse(Collections.emptySet())
                .stream().map(Long::valueOf).collect(Collectors.toList());
    }

    @Override
    public void add(long postId, Date date) {
        stringRedisTemplate.opsForSet().add(genKey(date), String.valueOf(postId));
    }

    @Override
    public void add(List<Long> postIdList, Date date) {
        stringRedisTemplate.opsForSet().add(genKey(date));
    }

    @Override
    public void remove(long postId, Date date) {
        stringRedisTemplate.opsForSet().remove(genKey(date), String.valueOf(postId));
    }

    @Override
    public void clear(Date date) {

    }

    private String genKey(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DateUtils.DEFAULT_PATTERN);
        String format = f.format(date);

        return RedisKeys.RECOMMEND_POST_ID.genKey(profiles, format);
    }
}
