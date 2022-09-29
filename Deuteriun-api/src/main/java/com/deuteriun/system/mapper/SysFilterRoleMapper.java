package com.deuteriun.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deuteriun.system.entity.SysFilterRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-15
 */
public interface SysFilterRoleMapper extends BaseMapper<SysFilterRole> {

    List<SysFilterRole> mixList(IPage<SysFilterRole>page,SysFilterRole sysFilterRole);

    List<SysFilterRole> listAllWithRole();
}
