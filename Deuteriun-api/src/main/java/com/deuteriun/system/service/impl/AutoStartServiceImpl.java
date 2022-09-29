package com.deuteriun.system.service.impl;

import com.deuteriun.system.service.AutoStartService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @ClassName AutoStartServiceImpl
 * @Description TODO
 * @Author gavin
 * @Date 29/5/2022 15:55
 * @Version 1.0
 **/
@Configuration
public class AutoStartServiceImpl {
    final
    List<AutoStartService> autoStartService;

    public AutoStartServiceImpl(List<AutoStartService> autoStartService) {
        this.autoStartService = autoStartService;
    }

    @PostConstruct
    void init() {
        for (AutoStartService startService : autoStartService) {
            startService.init();
        }
    }

}
