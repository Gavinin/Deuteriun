package com.deuteriun.system.utils;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "deuteriun")
public class DeuteriunConfigurationUtils {

    List<String> httpPermitAllAddressList;

}