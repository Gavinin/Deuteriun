package com.deuteriun.system.common.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "deuteriun")
public class ConstConfigure {
    public String token;

}
