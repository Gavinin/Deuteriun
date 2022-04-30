package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-01
 */
public interface SysLoginJwtBlacklistMapper extends BaseMapper<SysLoginJwtBlacklist> {

     SysLoginJwtBlacklist listByuserNameAndToken(String userName, String token);

     SysLoginJwtBlacklist listByuserName(String userName);

    Boolean deleteByUserName(String userName);
}
