package com.deuteriun.system.startup;

import com.deuteriun.system.cache.FilterRoleCacheConf;
import com.deuteriun.system.service.AutoStartService;
import org.springframework.stereotype.Component;

/**
 * @ClassName UrlRoleCacheImpl
 * @Description TODO
 * @Author gavin
 * @Date 29/5/2022 15:51
 * @Version 1.0
 **/
@Component
public class UrlRoleCacheImpl implements AutoStartService {

    final
    FilterRoleCacheConf filterRoleCacheConf;

    public UrlRoleCacheImpl(FilterRoleCacheConf filterRoleCacheConf) {
        this.filterRoleCacheConf = filterRoleCacheConf;
    }

    @Override
    public void init() {
        filterRoleCacheConf.refresh();
    }
}
