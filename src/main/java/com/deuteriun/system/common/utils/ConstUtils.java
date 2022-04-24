package com.deuteriun.system.common.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class ConstUtils implements InitializingBean {

    @Value("${deuteriun.front-token}")
    private String frontToken;


    public static String FRONT_TOKEN;


    @Override
    public void afterPropertiesSet() throws Exception {
        FRONT_TOKEN = frontToken;
    }
}
