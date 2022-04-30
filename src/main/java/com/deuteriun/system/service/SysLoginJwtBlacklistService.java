package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-01
 */
public interface SysLoginJwtBlacklistService extends IService<SysLoginJwtBlacklist> {

    SysLoginJwtBlacklist listByuserNameAndToken(String userName, String token);

    SysLoginJwtBlacklist listByuserName(String userName);

    Boolean delete(String userName);
}
