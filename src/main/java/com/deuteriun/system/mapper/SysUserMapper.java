package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByName(String userName);

    List<SysUser> getUsersByNames(List<String> index);

}
