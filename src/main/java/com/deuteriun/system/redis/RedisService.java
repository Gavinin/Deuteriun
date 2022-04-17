package com.deuteriun.system.redis;

public interface RedisService {

    Boolean put(String key,String value);

    String get(String key);
}
