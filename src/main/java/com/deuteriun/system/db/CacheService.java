package com.deuteriun.system.db;

public interface CacheService extends NoSqlInterface {

    Boolean put(String key,String value);

    String get(String key);

    Boolean delete(String key);

    Boolean refresh(String Key);
}
