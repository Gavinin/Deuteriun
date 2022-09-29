package com.deuteriun.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deuteriun.system.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-10
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> mixList(SysRole sysRole);

    boolean add(SysRole sysRole);

    boolean delete(SysRole sysRole);
}
