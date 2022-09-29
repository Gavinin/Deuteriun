package com.deuteriun.system.cache.redis;

import com.deuteriun.system.cache.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * IP Black list
 * @author gavin
 */
@Service("Redis2")
public class Redis2ServiceImpl implements CacheService {

    @Value("${spring.redis.db2.limit-login-expired}")
    private Long redisTimOut;

    /**
     * Redis key's timeout ,default timeout is 10 minutes.
     */
    public static Long REDIS_TIME_OUT = 10 * 60L;

    @PostConstruct
    public void postConstruct() {
        REDIS_TIME_OUT = redisTimOut * 60;
    }


    @Resource(name = "redisTemplate2")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void put(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception ignored) {

        }
    }

    @Override
    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception ignored) {

        }
        return null;
    }

    @Override
    public Boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception ignored) {

        }
        return false;
    }

    @Override
    public void expire(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value, REDIS_TIME_OUT, TimeUnit.SECONDS);
        } catch (Exception ignored) {

        }
    }


}
