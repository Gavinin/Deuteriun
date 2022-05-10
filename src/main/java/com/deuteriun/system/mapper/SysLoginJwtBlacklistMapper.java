package com.deuteriun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-01
 */
public interface SysLoginJwtBlacklistMapper extends BaseMapper<SysLoginJwtBlacklist> {

    SysLoginJwtBlacklist listByuserNameAndToken(String userName, String token);

    List<SysLoginJwtBlacklist> listByuserName(String userName);

    Boolean deleteByUserName(String userName);
}
