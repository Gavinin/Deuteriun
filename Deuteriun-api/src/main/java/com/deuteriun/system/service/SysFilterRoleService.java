package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysFilterRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deuteriun.system.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-15
 */
public interface SysFilterRoleService extends IService<SysFilterRole> {

    List<SysFilterRole> listAllWithRole();

    List<SysFilterRole> mixList(SysFilterRole sysFilterRole);

    boolean add(SysFilterRole sysFilterRole);

    boolean deleteById(SysFilterRole sysFilterRole);
}
