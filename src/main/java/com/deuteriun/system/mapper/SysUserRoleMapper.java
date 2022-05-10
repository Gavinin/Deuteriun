package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysUserRole;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     *
     * @param userId User ID from Sys_User Table
     * @return All info
     */
    List<SysUserRole> listAllByUserId(Long userId);


}
