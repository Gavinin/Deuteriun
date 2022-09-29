package com.deuteriun.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deuteriun.system.entity.SysUserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author gavin
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    List<GrantedAuthority> getGrantedAuthorityListById(Long id);

    List<SysUserRole> listAllByUserId(Long userId);

    List<SysUserRole> listAllByUserIds(List<Long> userList);

    Boolean add(SysUserRole sysUserRole);

    Boolean deleteById(SysUserRole sysUserRole);
}
