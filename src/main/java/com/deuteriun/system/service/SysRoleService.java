package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
public interface SysRoleService extends IService<SysRole> {

    List<GrantedAuthority> getGrantedAuthorityListById(Long id);


    List<SysRole> listAllByUserIds(List<Long> userList);


    boolean add(SysRole sysRole);

    Boolean delete(SysRole sysRole);
}
