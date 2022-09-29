package com.deuteriun.system.cache;

import com.deuteriun.system.entity.SysFilterRole;
import com.deuteriun.system.service.SysFilterRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName URLRoleCacheUtils
 * @Description This is a cache ,MUST DO NOT store data in there.
 * @Author gavin
 * @Date 19/5/2022 15:52
 * @Version 1.0
 **/
@Slf4j
@Component
public class FilterRoleCacheConf {
    private static final Map<String, List<String>> URL_ROLE_CACHE = new HashMap<>();

    final SysFilterRoleService sysFilterRoleService;

    public FilterRoleCacheConf(SysFilterRoleService sysFilterRoleService) {
        this.sysFilterRoleService = sysFilterRoleService;
    }

    public static Map<String, List<String>> getUrlRoleCache() {
        return URL_ROLE_CACHE;
    }

    public void refresh() {
        List<SysFilterRole> list = sysFilterRoleService.listAllWithRole();
        if (list.size() > 0) {
            //For performance using fori
            for (int i = 0; i < list.size(); i++) {
                String filter = list.get(i).getFilter();
                //Check url weather has existent.
                if (URL_ROLE_CACHE.get(filter) != null) {
                    log.warn("Address has existent!!");
                    continue;
                }
                List<String> currentFilterRoles = new ArrayList<>();
                for (int j = 0; j < list.size(); j++) {
                    SysFilterRole sysFilterRole = list.get(j);
                    if (filter.equals(sysFilterRole.getFilter())) {
                        currentFilterRoles.add(sysFilterRole.getSysRole());
                    }
                }
                URL_ROLE_CACHE.put(filter, currentFilterRoles);
            }
        }
    }


}
