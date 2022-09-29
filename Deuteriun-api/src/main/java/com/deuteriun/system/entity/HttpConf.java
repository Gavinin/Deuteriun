package com.deuteriun.system.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gavin
 */
@Data
@Component
@ConfigurationProperties(prefix = "deuteriun.http")
public class HttpConf {
    List<String> permitAllAddressList;

}
