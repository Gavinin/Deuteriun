package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysUserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface SysUserRoleService {

    List<GrantedAuthority> getGrantedAuthorityListById(Long id);

    List<SysUserRole> listAllByUserIds(List<Long> userList);
}
