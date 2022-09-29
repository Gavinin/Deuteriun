package com.deuteriun.system.cache;

/**
 * Cache interface ,any cache service should implement this
 * the interface defined 3 base operate put ,get and delete
 *
 * @author Gavin
 */
public interface CacheService {
    /**
     * Put
     * @param key is key
     * @param value is value
     */
    void put(String key, String value);
    /**
     * Get value from key
     * @param key you saved
     * @return value
     */
    String get(String key);
    /**
     * Delete from key
     * @param key is key
     * @return is finish
     */
    Boolean delete(String key);

    /**
     * Save in Cache service with expire time in yaml
     * @param key
     * @param value
     */
    void expire(String key, String value);
}
