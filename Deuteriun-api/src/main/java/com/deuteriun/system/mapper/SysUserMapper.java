package com.deuteriun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deuteriun.system.entity.SysUser;

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

    IPage<SysUser> getUsersByNames(IPage<SysUser> page, List<String> index);

}
