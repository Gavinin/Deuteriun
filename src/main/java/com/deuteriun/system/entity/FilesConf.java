package com.deuteriun.system.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @ClassName AllowSuffixEntity
 * @Description TODO
 * @Author gavin
 * @Date 10/5/2022 01:21
 * @Version 1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "deuteriun.files")
public class FilesConf {
    List<String> allowSuffix;
    private static List<String> ALLOW_SUFFIX;

    public static List<String> getAllowSuffix() {
        return ALLOW_SUFFIX;
    }

    @PostConstruct
    public void postConstruct() {
        ALLOW_SUFFIX = allowSuffix;

    }

}
