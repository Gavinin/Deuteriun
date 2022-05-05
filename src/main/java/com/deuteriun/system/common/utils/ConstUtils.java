package com.deuteriun.system.common.utils;

import com.deuteriun.system.db.redis.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@Component
public class ConstUtils {

    @Value("${deuteriun.front-token}")
    private String frontToken;

    @Value("${deuteriun.front-date-format}")
    private String frontDateFormat;

    @Value("${deuteriun.redis.timeout}")
    private Long redisTimOut;

    public static String FRONT_TOKEN;

    public static SimpleDateFormat DATE_TIME_FORMAT;

    @PostConstruct
    public void postConstruct() {
        FRONT_TOKEN = frontToken;
        DATE_TIME_FORMAT = new SimpleDateFormat(frontDateFormat);
        RedisServiceImpl.REDIS_TIME_OUT = redisTimOut * 60;
    }


}
