package com.deuteriun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deuteriun.system.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserById(Long userId);

    SysUser getUserByName(String userName);

    void getUserWithRoles(SysUser user);

    List<SysUser> listAllUsers();

    List<SysUser> getAllUserWithRoles(IPage<SysUser> page);

    List<SysUser> getAllUserWithRoles(List<String> users, IPage<SysUser> page);

    SysUser add(SysUser user);

    Boolean update(SysUser user);

    Boolean del(SysUser user);

}
