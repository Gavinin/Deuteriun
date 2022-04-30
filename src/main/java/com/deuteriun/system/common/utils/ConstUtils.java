package com.deuteriun.system.common.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Component
public class ConstUtils implements InitializingBean {

    @Value("${deuteriun.front-token}")
    private String frontToken;

    @Value("${deuteriun.front-date-format}")
    private String frontDateFormat;

    public static String FRONT_TOKEN;

    public static SimpleDateFormat DATE_TIME_FORMAT;

    @Override
    public void afterPropertiesSet() throws Exception {
        FRONT_TOKEN = frontToken;
        DATE_TIME_FORMAT = new SimpleDateFormat(frontDateFormat);
    }


}
