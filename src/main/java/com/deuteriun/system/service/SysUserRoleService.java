package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 *  SysUserRoleService recode user role
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    List<GrantedAuthority> getGrantedAuthorityListById(Long id);

}
