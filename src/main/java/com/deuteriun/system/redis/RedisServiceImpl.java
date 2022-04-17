package com.deuteriun.system.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Boolean put(String key,String value) {
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
