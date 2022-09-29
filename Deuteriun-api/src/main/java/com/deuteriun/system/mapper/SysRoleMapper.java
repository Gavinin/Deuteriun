package com.deuteriun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deuteriun.system.entity.SysFiles;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.entity.SysUser;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> mixList(IPage<SysRole> page, SysRole sysRole);


}
