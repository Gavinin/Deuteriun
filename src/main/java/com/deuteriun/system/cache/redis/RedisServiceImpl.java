package com.deuteriun.system.cache.redis;

import com.deuteriun.system.cache.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements CacheService {

    @Value("${deuteriun.redis.timeout}")
    private Long redisTimOut;

    public static Long REDIS_TIME_OUT = 60 * 10L;

    @PostConstruct
    public void postConstruct() {
        RedisServiceImpl.REDIS_TIME_OUT = redisTimOut * 60;
    }


    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean put(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value, REDIS_TIME_OUT, TimeUnit.SECONDS);
            return true;
        } catch (Exception ignored) {

        }
        return false;
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
    public Boolean expire(String key) {
        try {
            redisTemplate.boundValueOps(key).getExpire();
            return true;

        } catch (Exception ignored) {

        }
        return false;
    }
}
