package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deuteriun.system.entity.SysUserRoleDTO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<SysUserRoleDTO> listAllByUserId(Long userId);


}
