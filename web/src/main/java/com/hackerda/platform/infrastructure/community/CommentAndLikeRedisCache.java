package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.domain.community.CommentCountService;
import com.hackerda.platform.domain.community.LikeCountService;
import com.hackerda.platform.domain.community.LikeType;
import com.hackerda.platform.domain.constant.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentAndLikeRedisCache implements CommentCountService, LikeCountService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public long increment(long postId) {
        Optional<Long> increment = Optional.ofNullable(stringRedisTemplate.opsForValue().increment(getCommentKey(postId)));

        return increment.orElse(0L);
    }

    @Override
    public long decrement(long postId) {
        Optional<Long> decrement = Optional.ofNullable(stringRedisTemplate.opsForValue().decrement(getCommentKey(postId)));

        return decrement.orElse(0L);
    }

    @Override
    public long count(long postId) {
        Optional<String> value = Optional.ofNullable(stringRedisTemplate.opsForValue().get(getCommentKey(postId)));
        return Long.parseLong(value.orElse("0"));
    }

    @Override
    public void setCount(long postId, long size) {
        stringRedisTemplate.opsForValue().set(getCommentKey(postId), String.valueOf(size));
    }

    @Override
    public void increment(LikeType likeType, long likeContentId, String userName) {
        stringRedisTemplate.opsForSet().add(getLikeKey(likeType, likeContentId), userName);
    }

    @Override
    public void decrement(LikeType likeType, long likeContentId, String userName) {
        stringRedisTemplate.opsForSet().remove(getLikeKey(likeType, likeContentId), userName);
    }

    @Override
    public boolean hasLike(LikeType likeType, long likeContentId, String userName) {
        return Optional.ofNullable(stringRedisTemplate.opsForSet().isMember(getLikeKey(likeType, likeContentId),
                userName)).orElse(false);
    }

    @Override
    public long likeCount(LikeType likeType, long likeContentId) {
        return Optional.ofNullable(stringRedisTemplate.opsForSet().size(getLikeKey(likeType, likeContentId))).orElse(0L);
    }

    @Override
    public void setCount(LikeType likeType, long likeContentId, Collection<String> userNameCollection) {
        if(!userNameCollection.isEmpty()) {
            String[] userNameArray = userNameCollection.toArray(new String[0]);
            stringRedisTemplate.opsForSet().add(getLikeKey(likeType, likeContentId), userNameArray);
        }
    }

    public void reset() {
        Set<String> commentKeys = stringRedisTemplate.keys(RedisKeys.COMMENT_COUNT.genKey(profiles) + "*");
        stringRedisTemplate.delete(commentKeys);

        Set<String> likeKeys = stringRedisTemplate.keys(RedisKeys.LIKE_STATUS.genKey(profiles) + "*");
        stringRedisTemplate.delete(likeKeys);
    }

    public String getCommentKey(long postId) {
        return RedisKeys.COMMENT_COUNT.genKey(profiles, String.valueOf(postId));
    }

    public String getLikeKey(LikeType likeType, long contentId) {
        return RedisKeys.LIKE_STATUS.genKey(profiles, likeType.name(), String.valueOf(contentId));
    }
}
