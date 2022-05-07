package com.deuteriun.system.cache;

public interface CacheService extends NoSqlInterface {

    Boolean put(String key, String value);

    String get(String key);

    Boolean delete(String key);

    Boolean expire(String key);
}
