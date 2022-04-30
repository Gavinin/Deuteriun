package com.deuteriun.system.db.redis;

import com.deuteriun.system.db.CacheService;
import com.deuteriun.system.db.NoSqlInterface;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements CacheService, NoSqlInterface {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean put(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
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
    public Boolean refresh(String Key) {
        try {

            return true;

        } catch (Exception ignored) {

        }
        return false;
    }
}
