package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {


    List<SysRole> mixList();
}
