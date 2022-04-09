package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserByName(String userName);

}
