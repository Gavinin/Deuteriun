package com.deuteriun.system.utils;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author gavin
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "deuteriun")
public class DeuteriunConfigurationUtils {
    public static boolean ANYONE_PERMISSION = false;
    Boolean anyonePermission;

    List<String> httpPermitAllAddressList;

    List<String> allowSuffix;

    @PostConstruct
    private void init() {
        if (anyonePermission != null) {
            ANYONE_PERMISSION = anyonePermission;
        }
    }

}
